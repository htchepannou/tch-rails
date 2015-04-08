/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author herve
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (value={ElementType.TYPE, ElementType.METHOD})
public @interface  WebTemplate
{
    String name ();

    String head () default "";
    
    String leftSidebar() default "";

    String rightSidebar() default "";

    @Deprecated
    String title () default "";

    @Deprecated
    String[] css() default "";

    @Deprecated
    String[] js() default "";
}
