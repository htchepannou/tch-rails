package com.tchepannou.rails.core.annotation;

import com.tchepannou.rails.core.api.ActiveRecord;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicate an {@link ActionController}
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (value={ElementType.TYPE})
public @interface  Action
{
    String name() default "";

    Class<? extends ActiveRecord> modelClass () default ActiveRecordDefault.class;


    //-- Inner classes
    /**
     * Default value for ActiveRecord;
     * See <a href="http://stackoverflow.com/questions/1178104/error-setting-a-default-null-value-for-an-annotations-field">this link</a>
     */
    public static class ActiveRecordDefault
        extends ActiveRecord
    {
        @Override
        public long getId ()
        {
            throw new UnsupportedOperationException ("Not supported yet.");
        }

        @Override
        public void setId (long id)
        {
            throw new UnsupportedOperationException ("Not supported yet.");
        }
    }
}
