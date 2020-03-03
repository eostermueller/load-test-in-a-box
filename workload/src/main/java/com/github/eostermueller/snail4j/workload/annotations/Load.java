package com.github.eostermueller.snail4j.workload.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * snail4j is a load-test-in-a-box; it presents the user with a list of classes, and you select which one(s) you want to apply load to.
 * Once you annotate you method with this annotation, snail4j will enable you to apply load to that method.
 * This class has 'protoype' scope, as described here:
 * https://docs.spring.io/spring/docs/5.2.3.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-prototype
 * @author eoste
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Load {
	
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
