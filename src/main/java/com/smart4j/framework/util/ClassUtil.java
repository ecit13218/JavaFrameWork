package com.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Administrator on 2017/7/17.
 */
public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("类文件找不到", e);
        }
        return clazz;
    }

    /**
     * 获取包名下所有类
     *
     * @param packgeName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packgeName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packgeName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packgePath = url.getPath().replaceAll("%20", "");
                        addClass(classSet, packgePath, packgeName);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                                while (jarEntryEnumeration.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).
                                                replaceAll("/", ".");
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            LOGGER.error("获取类失败",e);
            throw new RuntimeException();
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files=new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile()&&file.getName().endsWith(".class"))||file.isDirectory();
            }
        });
            for (File file:files){
                String fileName=file.getName();
                if(file.isFile()){
                    String className=fileName.substring(0,fileName.lastIndexOf("."));
                    if(StringUtils.isNotEmpty(packageName)){
                        className=packageName+"."+className;
                    }
                    doAddClass(classSet,className);
                }else{
                    String subPackgePath=fileName;
                    if (StringUtils.isNotEmpty(packagePath)){
                        subPackgePath=packagePath+"."+subPackgePath;
                    }
                    String subPackgeName=fileName;
                    if(StringUtils.isNotEmpty(packageName)){
                        subPackgeName=packageName+"."+subPackgeName;
                    }
                    addClass(classSet,subPackgePath,subPackgeName);
                }
            }

    }
    private static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> clazz=loadClass(className,false);
        classSet.add(clazz);
    }
}
