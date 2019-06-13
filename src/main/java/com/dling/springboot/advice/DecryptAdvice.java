package com.dling.springboot.advice;

import com.dling.springboot.config.AppProperties;
import com.dling.springboot.kit.CryptKit;
import com.dling.springboot.kit.HttpKit;
import com.dling.springboot.kit.IOKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DecryptAdvice implements RequestBodyAdvice {

    @Autowired
    private AppProperties appProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
//        return methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        System.out.println("类  名："+methodParameter.getContainingClass().getCanonicalName()+"\t方法名："+methodParameter.getMethod().getName());
        String body = HttpKit.readData(httpInputMessage.getBody(), "utf-8");
        System.out.println("请求密文---->" + body);
        try {
            body = CryptKit.base64Decrypt(appProperties.getSecretKey(), appProperties.getSecretIv(), body);
            System.out.println("请求明文---->" + body);
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        Map<String, String> data = new HashMap<>();
        data.put("body", body);
        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(data.get("body").getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public HttpHeaders getHeaders() {
                return httpInputMessage.getHeaders();
            }
        };
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        // System.out.println("afterBodyRead---->" + JSON.toJSONString(o));
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
