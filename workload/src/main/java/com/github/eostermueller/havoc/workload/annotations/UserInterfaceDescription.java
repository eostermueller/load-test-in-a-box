package com.github.eostermueller.havoc.workload.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ METHOD, PARAMETER })
public @interface UserInterfaceDescription {
	public static final String VALUE="value";
	public static final String LOCALE="locale";
	public String value();
	
	/**
	 * IETF BCP 47 standard
	 * https://tools.ietf.org/html/bcp47
	 * https://en.wikipedia.org/wiki/IETF_language_tag
	 * 
	 * note that a dash (-) is used instead of an underscore (_).
	 * Why "IETF BCP 47"?  b/c java says here:
	 * https://docs.oracle.com/javase/tutorial/i18n/locale/create.html
	 * that it supports it.
	 * @return
	 */
	public String locale() default "en-US";
}
