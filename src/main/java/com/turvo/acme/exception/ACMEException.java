package com.turvo.acme.exception;

public class ACMEException extends Exception {

    private ExceptionMessage exceptionMessage;
    private Object[] params;

    public ACMEException(ExceptionMessage exceptionMessage){
        super();
        this.exceptionMessage  = exceptionMessage;
    }

    public ACMEException(ExceptionMessage exceptionMessage, Object[] params){
        this(exceptionMessage);
        this.params = params;
    }

    public ACMEException(ExceptionMessage exceptionMessage, Object[] params, Throwable t){
        super(t);
        this.exceptionMessage = exceptionMessage;
        this.params = params;
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

    public Object[] getParams() {
        return params;
    }

}
