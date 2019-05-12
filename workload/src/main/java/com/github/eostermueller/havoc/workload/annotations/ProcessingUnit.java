package com.github.eostermueller.havoc.workload.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ProcessingUnit {
	public static final String USE_CASE = "useCase";
	public static final String SELECTED = "selected";
	public static final String VALUE = "value";
	
 	public String useCase();

 	public boolean selected() default false;
 	/**
 	 * Human read-able description of the processing.
 	 * one value in this array for each language translation/locale you choose to support.
 	 * @return
 	 */
 	public UserInterfaceDescription[] value();
}
