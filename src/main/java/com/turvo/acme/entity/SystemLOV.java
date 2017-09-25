package com.turvo.acme.entity;

import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.registry.Entity;


@Entity(queryPrefix = "sylov", module = "common")
public class SystemLOV implements ACMEEntity {

    private long id;
    private String name;
    private String value;
    private String type;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemLOV{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
