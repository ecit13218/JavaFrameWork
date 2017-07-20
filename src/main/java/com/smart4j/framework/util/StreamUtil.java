package com.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流工具操作类
 * Created by Administrator on 2017/7/19.
 */
public final class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /* public static String getString(InputStream is) {
         StringBuilder sb = new StringBuilder();
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));
         String line;
         try {
             while ((line = reader.readLine()) != null) {
                 sb.append(line);
             }
         } catch (IOException e) {
             LOGGER.error("从输入流中获取字符串失败", e);
             throw new RuntimeException();
         }
         return sb.toString();
     }*/
    public static String getString(InputStream is) {
        byte[] bytes = new byte[1024 * 1024];
        String str;
        try {
            int nRead = 1;
            int nTotalRead = 0;
            while (nRead > 0) {
                nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
                if (nRead > 0)
                    nTotalRead = nTotalRead + nRead;
            }
            str = new String(bytes, 0, nTotalRead, "utf-8");
        } catch (IOException e) {
            LOGGER.error("从输入流中获取字符串失败", e);
            throw new RuntimeException();
        }
        return str;
    }
}
