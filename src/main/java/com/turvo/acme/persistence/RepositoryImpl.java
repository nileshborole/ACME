package com.turvo.acme.persistence;

import com.turvo.acme.exception.ACMEException;
import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.entity.AuditableEntity;
import com.turvo.acme.persistence.registry.Entity;
import com.turvo.acme.persistence.registry.EntityListener;
import com.turvo.acme.persistence.registry.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("repository")
public class RepositoryImpl implements Repository {

    @Autowired
    private ApplicationContext context;

    @Override
    public ACMEEntity create(ACMEEntity entity) throws ACMEException {
        Entity meta = EntityUtil.getEntityMeta(entity);
        EntityListener listener = getEntityListener(meta);
        try {

            if(entity instanceof AuditableEntity){
                ((AuditableEntity) entity).setCreatedBy(1);
                ((AuditableEntity) entity).setCreatedOn(new Date());
                ((AuditableEntity) entity).setUpdatedBy(1);
                ((AuditableEntity) entity).setUpdatedOn(new Date());
                ((AuditableEntity) entity).setStatus(1);
            }

            entity = listener.preCreate(entity, meta);
            entity = listener.create(entity, meta);
            entity = listener.postCreate(entity, meta);
        }catch (Exception e){
            throw new ACMEException(PersistenceExceptionMessage.DATABASE_ERROR, new Object[]{"create error", entity }, e);
        }
        return entity;
    }

    @Override
    public ACMEEntity update(ACMEEntity entity) throws ACMEException {
        Entity meta = EntityUtil.getEntityMeta(entity);
        EntityListener listener = getEntityListener(meta);
        try{
            if(entity instanceof AuditableEntity){
                ((AuditableEntity) entity).setUpdatedOn(new Date());
                ((AuditableEntity) entity).setUpdatedBy(1);
            }

            entity = listener.preUpdate(entity, meta);
            entity = listener.update(entity, meta);
            entity = listener.postUpdate(entity, meta);
        }catch (Exception e){
            throw new ACMEException(PersistenceExceptionMessage.DATABASE_ERROR, new Object[]{"update error", entity }, e);
        }
        return entity;
    }

    @Override
    public void delete(long id, Class<? extends ACMEEntity> clazz) throws ACMEException {
        Entity meta = EntityUtil.getEntityMeta(clazz);
        EntityListener listener = getEntityListener(meta);
        try{
            listener.delete(id, meta);
            listener.postDelete(id, meta);
        }catch (Exception e){
            throw new ACMEException(PersistenceExceptionMessage.DATABASE_ERROR, new Object[]{"delete error", id, clazz.getName() }, e);
        }
    }

    @Override
    public List find(String query, Map<String, Object> params) throws ACMEException {
        return find(query, params, null);
    }

    @Override
    public List find(String query, Map<String, Object> params, Class<? extends ACMEEntity> clazz) throws ACMEException {
        EntityListener listener = clazz == null? getDefaultEntityListener() : getEntityListener(EntityUtil.getEntityMeta(clazz));
        try{
            return listener.find(query, params);
        }catch (Exception e){
            throw new ACMEException(PersistenceExceptionMessage.DATABASE_ERROR, new Object[]{"find query", query, params});
        }
    }

    @Override
    public void fireAdhoc(String query, Map<String, Object> params) throws ACMEException{
        fireAdhoc(query, params, null);
    }

    @Override
    public void fireAdhoc(String query, Map<String, Object> params, Class<? extends ACMEEntity> clazz) throws ACMEException {
        EntityListener listener = clazz == null? getDefaultEntityListener() : getEntityListener(EntityUtil.getEntityMeta(clazz));
        try{
            listener.fireAdhoc(query, params);
        }catch (Exception e){
            throw new ACMEException(PersistenceExceptionMessage.DATABASE_ERROR, new Object[]{"adhoc query",query, params});
        }
    }

    @Override
    public <T extends ACMEEntity> T restore(long id, Class<T> clazz) throws ACMEException {
        Entity meta = EntityUtil.getEntityMeta(clazz);
        EntityListener listener = getEntityListener(meta);
        ACMEEntity entity = null;
        try{
            listener.preRestore(id, meta);
            entity = listener.restore(id, meta);
            entity = listener.postRestore(entity, meta);
        }catch (Exception e){
            throw new ACMEException(PersistenceExceptionMessage.DATABASE_ERROR, new Object[]{"restore entity",id, clazz.getName()});
        }

        return (T)entity;
    }

    private EntityListener getEntityListener(Entity meta){
        return (EntityListener) context.getBean(meta.listener());
    }

    private EntityListener getDefaultEntityListener(){
        return (EntityListener) context.getBean("rootListener");
    }

}
