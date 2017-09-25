package com.turvo.acme.persistence.listener;

import com.turvo.acme.exception.ACMEException;
import com.turvo.acme.persistence.EntityUtil;
import com.turvo.acme.persistence.PersistenceExceptionMessage;
import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.registry.Dao;
import com.turvo.acme.persistence.registry.Entity;
import com.turvo.acme.persistence.registry.EntityListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("rootListener")
public class BaseListener implements EntityListener {

    @Autowired
    private Dao dao;

    public void setDao(Dao dao){
        this.dao = dao;
    }

    @Override
    public ACMEEntity preCreate(ACMEEntity entity, Entity meta) throws Exception {
        return entity;
    }

    @Override
    public ACMEEntity create(ACMEEntity entity, Entity meta) throws Exception {
        dao.insert(EntityUtil.getQueryName(meta)+"_insert", entity);
        return entity;
    }

    @Override
    public ACMEEntity postCreate(ACMEEntity entity, Entity meta) throws Exception {
        return entity;
    }

    @Override
    public ACMEEntity preUpdate(ACMEEntity entity, Entity meta) throws Exception {
        return entity;
    }

    @Override
    public ACMEEntity update(ACMEEntity entity, Entity meta) throws Exception {
        dao.update(EntityUtil.getQueryName(meta)+"_update", entity);
        return entity;
    }

    @Override
    public ACMEEntity postUpdate(ACMEEntity entity, Entity meta) throws Exception {
        return entity;
    }

    @Override
    public long preRestore(long id, Entity meta) throws Exception {
        return id;
    }

    @Override
    public <T extends ACMEEntity> T restore(long id, Entity meta) throws Exception {
        String query = EntityUtil.getQueryName(meta)+"_select";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        List entities = dao.find(query, params);

        if(entities ==null || entities.isEmpty())
            throw new ACMEException(PersistenceExceptionMessage.INVALID_ID, new Object[]{id, meta.queryPrefix()});

        return (T)entities.get(0);
    }

    @Override
    public ACMEEntity postRestore(ACMEEntity entity, Entity meta) throws Exception {
        return entity;
    }

    @Override
    public List find(String query, Map<String, Object> params) throws Exception {
        return dao.find(query, params);
    }

    @Override
    public void delete(long id, Entity meta) throws Exception {
        dao.delete(EntityUtil.getQueryName(meta)+"_delete", id);
    }

    @Override
    public void postDelete(long id, Entity meta) throws Exception {
    }

    @Override
    public void fireAdhoc(String query, Map<String, Object> params) throws Exception {
        if(query.endsWith("_insert")){
            dao.insert(query, params);
        }else if(query.endsWith("_update")){
            dao.update(query, params);
        }else if(query.endsWith("delete")){
            dao.delete(query, params);
        }
    }
}
