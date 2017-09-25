package com.turvo.acme.persistence.dao;

import com.turvo.acme.persistence.registry.Dao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SqlDao implements Dao {

    private final SqlSession sqlSession;

    public SqlDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.sqlSession.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
    }

    @Override
    public Object insert(String query, Object object) throws Exception{
        return this.sqlSession.insert(query, object);
    }

    @Override
    public Object update(String query, Object object) throws Exception{
        return this.sqlSession.update(query, object);
    }

    @Override
    public List find(String query, Map<String, Object> object) throws Exception{
        return this.sqlSession.selectList(query, object);
    }

    @Override
    public void delete(String query, Object object) throws Exception{
        this.sqlSession.delete(query, object);
    }
}
