package com.dling.springboot.interceptor;

import com.alibaba.fastjson.JSON;
import com.dling.springboot.kit.CryptKit;
import com.dling.springboot.kit.HttpKit;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dling.springboot.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @description appId验证，时间戳验证，数据加解密 拦截器
 * @author dling
 * @date 2019-06-10 22:53:16
 * @since jdk8
 */
public class DataInterceptor implements HandlerInterceptor {
    public static final String M_GET = "GET";
    public static final String M_POST = "POST";

    @Autowired
    private AppProperties appProperties;

    // 在请求执行前执行的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//        AppProperties appProperties = applicationContext.getBean(AppProperties.class);
        System.out.println("preHandle()...");
        String method = request.getMethod();
        if (M_GET.equalsIgnoreCase(method)) {
            System.out.println("parameterMap-->"+JSON.toJSONString(request.getParameterMap()));
            return true;
        } else if (M_POST.equalsIgnoreCase(method)) {
            String encryptData = HttpKit.readData(request);
            System.out.println("密文-->"+encryptData);
            String str = CryptKit.base64Decrypt(appProperties.getSecretKey(), appProperties.getSecretIv(), encryptData);
            System.out.println("明文-->"+str);
            return true;
        } else {
            return false;
        }
    }

    // 在请求执行后执行的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle()...");
    }

    // 在视图渲染后执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion().");
    }
}
