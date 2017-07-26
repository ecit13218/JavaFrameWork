package com.smart4j.framework;

import com.smart4j.framework.bean.Data;
import com.smart4j.framework.bean.Handler;
import com.smart4j.framework.bean.Param;
import com.smart4j.framework.bean.View;
import com.smart4j.framework.helper.BeanHelper;
import com.smart4j.framework.helper.ConfigHelper;
import com.smart4j.framework.helper.ControllerHelper;
import com.smart4j.framework.helper.HelpLoader;
import com.smart4j.framework.util.CodecUtil;
import com.smart4j.framework.util.JsonUtil;
import com.smart4j.framework.util.ReflectionUtil;
import com.smart4j.framework.util.StreamUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 * Created by Administrator on 2017/7/19.
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        HelpLoader.init();//初始化相关Helper类
        ServletContext servletContext = config.getServletContext();//获取ServletContext对象
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");//注册处理JSP的servlet
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getMethod().toLowerCase();//获取请求方法与请求路径
        String requestPath = request.getServletPath();
        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);//创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params = StringUtils.split(body, "&");
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param :
                            params) {
                        String[] array = StringUtils.split(param, "=");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            String paramname = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramname, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();//调用Action方法
            Object result = null;
            if (param.getParamMap().size() > 0) {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);//处理Action方法返回值
            } else
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        response.sendRedirect(request.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() +"/"+ path).forward(request, response);
                    }
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }

        }
    }
}
