package com.turvo.acme.persistence.registry;

import com.turvo.acme.exception.ACMEException;
import com.turvo.acme.persistence.entity.ACMEEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface Repository {

    @Transactional
    public ACMEEntity create(ACMEEntity entity) throws ACMEException;

    @Transactional
    public ACMEEntity update(ACMEEntity entity) throws ACMEException;

    @Transactional
    public void delete(long id, Class<? extends ACMEEntity> clazz) throws ACMEException;

    public List find(String query, Map<String, Object> params) throws ACMEException;

    public List find(String query, Map<String, Object> params, Class<? extends ACMEEntity> clazz) throws ACMEException;

    @Transactional
    public void fireAdhoc(String query, Map<String, Object> params) throws ACMEException;

    @Transactional
    public void fireAdhoc(String query, Map<String, Object> params, Class<? extends ACMEEntity> clazz) throws ACMEException;

    public <T extends ACMEEntity> T restore(long id, Class<T> clazz) throws ACMEException;

}
