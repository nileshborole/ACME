package com.turvo.acme.persistence.registry;


import java.util.List;
import java.util.Map;

public interface Dao {

    public Object insert(String query, Object object) throws Exception;

    public Object update(String query, Object object) throws Exception;

    public List find(String query, Map<String, Object> object) throws Exception;

    public void delete(String query, Object object) throws Exception;

}
