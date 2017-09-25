package com.turvo.acme.persistence;

import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.registry.Entity;

import java.lang.annotation.Annotation;

public class EntityUtil {

    public static Entity getEntityMeta(ACMEEntity entity){
        return getEntityMeta(entity.getClass());
    }

    public static Entity getEntityMeta(Class clazz){
        Annotation annotation = clazz.getAnnotation(Entity.class);
        if(annotation == null)
            throw new IllegalArgumentException("Illegal entity passed. Cannot determine the annotation");
        return (Entity) annotation;
    }

    public static String getQueryName(Entity entity){
        return entity.module()+"."+entity.queryPrefix().toLowerCase();
    }




}
