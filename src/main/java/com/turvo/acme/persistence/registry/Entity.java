package com.turvo.acme.persistence.registry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

    String queryPrefix();
    String module();
    String listener() default "rootListener";

}
