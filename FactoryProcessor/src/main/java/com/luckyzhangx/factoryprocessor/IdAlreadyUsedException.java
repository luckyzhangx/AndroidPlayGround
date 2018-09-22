package com.luckyzhangx.factoryprocessor;

public class IdAlreadyUsedException extends Exception {

    private FactoryAnnotatedClass clazz;

    public IdAlreadyUsedException(String s) {
        super(s);
    }

    public IdAlreadyUsedException(FactoryAnnotatedClass clazz) {
        super(clazz.toString());
        this.clazz = clazz;
    }

    public FactoryAnnotatedClass getExisting() {
        return clazz;
    }
}
