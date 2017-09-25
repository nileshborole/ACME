package com.turvo.acme.test;

import com.turvo.acme.entity.SystemLOV;
import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.registry.Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    private SystemLOV entity;

    @Autowired
    private Repository repository;

    @Before
    public void init(){
        this.entity = new SystemLOV();
        entity.setName("test");
        entity.setType("test-type");
        entity.setValue("test-value");
    }

    @Test
    public void testCreate() throws Exception{
        ACMEEntity actual = repository.create(this.entity);
        if(actual.getId() > 0)
            assertTrue(true);
        else
            assertTrue(false);
    }

}
