package com.turvo.acme.persistence;

import com.turvo.acme.exception.ExceptionMessage;

public enum PersistenceExceptionMessage implements ExceptionMessage {

    DATABASE_ERROR("database_error"),
    UNKNOWN_EXCEPTION("unknown_exception"),
    INVALID_ID("invalid_id");

    private String id;
    private String description;

    @Override
    public String id() {
        return this.id;
    }


    private PersistenceExceptionMessage(String id){
        this.id = id;
    }


}
