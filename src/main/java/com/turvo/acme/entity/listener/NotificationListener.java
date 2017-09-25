package com.turvo.acme.entity.listener;

import com.turvo.acme.entity.notif.Notification;
import com.turvo.acme.entity.notif.NotificationQuery;
import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.listener.BaseListener;
import com.turvo.acme.persistence.registry.Entity;
import com.turvo.acme.persistence.registry.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component("notificationListener")
public class NotificationListener extends BaseListener {

    @Autowired
    private Repository repository;

    @Override
    public ACMEEntity postCreate(ACMEEntity entity, Entity meta) throws Exception {

        Notification notification = (Notification)entity;

        if(notification.getParams() != null && !notification.getParams().isEmpty()){

            for(Map.Entry<String, String> entry : notification.getParams().entrySet()){
                Map<String, Object> params = new HashMap<>();
                params.put("name", entry.getKey());
                params.put("value", entry.getValue());
                params.put("alertId", notification.getId());
                repository.fireAdhoc(NotificationQuery.NOTIF_PARAM_INSERT.query(), params);
            }
        }

        return super.postCreate(entity, meta);
    }
}
