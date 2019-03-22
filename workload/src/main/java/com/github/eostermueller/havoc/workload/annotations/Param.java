package com.github.eostermueller.havoc.workload.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * 
 * @author erikostermueller
 * similar t0: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html
 */
@Target(value=ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
	public String name();
	public String defaultValue() default "";
 	public UserInterfaceDescription[] value() default {};
}
