package com.turvo.acme.persistence.registry;

import com.turvo.acme.persistence.entity.ACMEEntity;

import java.util.List;
import java.util.Map;

public interface EntityListener {

    public ACMEEntity preCreate(ACMEEntity entity, Entity meta ) throws Exception;
    public ACMEEntity create(ACMEEntity entity, Entity meta) throws Exception;
    public ACMEEntity postCreate(ACMEEntity entity, Entity meta) throws Exception;

    public ACMEEntity preUpdate(ACMEEntity entity, Entity meta) throws Exception;
    public ACMEEntity update(ACMEEntity entity, Entity meta) throws Exception;
    public ACMEEntity postUpdate(ACMEEntity entity, Entity meta) throws Exception;

    public long preRestore(long id, Entity meta) throws Exception;
    public <T extends ACMEEntity> T restore(long id, Entity meta) throws Exception;
    public ACMEEntity postRestore(ACMEEntity entity, Entity meta) throws Exception;

    public List find(String query, Map<String, Object> params) throws Exception;

    public void delete(long id, Entity meta) throws Exception;
    public void postDelete(long id, Entity meta) throws Exception;

    public void fireAdhoc(String query, Map<String, Object> params) throws Exception;

}
