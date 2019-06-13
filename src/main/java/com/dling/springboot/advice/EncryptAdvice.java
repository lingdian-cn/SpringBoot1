package com.dling.springboot.advice;

import com.dling.springboot.annotation.Encrypt;
import com.dling.springboot.config.AppProperties;
import com.dling.springboot.kit.CryptKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class EncryptAdvice implements ResponseBodyAdvice<String> {

    @Autowired
    private AppProperties appProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
//        return true;
        return methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public String beforeBodyWrite(String body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            System.out.println("响应明文---->" + body);
            body = CryptKit.base64Encrypt(appProperties.getSecretKey(), appProperties.getSecretIv(), body);
            System.out.println("响应密文---->" + body + "\n\n");
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } finally {
            return body;
        }
    }

}
