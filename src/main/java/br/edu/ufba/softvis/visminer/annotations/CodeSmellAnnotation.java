package br.edu.ufba.softvis.visminer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CodeSmellAnnotation {
	
	/**
	 * @return code smell name.
	 */
	public String name();

	/**
	 * @return code smell description.
	 */
	public String description();

}
