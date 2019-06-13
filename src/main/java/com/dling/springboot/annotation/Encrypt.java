package com.dling.springboot.annotation;

import java.lang.annotation.*;

/**
 * 进行参数加密和解密
 *
 * @author huan.fu
 * @date 2018/9/28 - 16:08
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
}
