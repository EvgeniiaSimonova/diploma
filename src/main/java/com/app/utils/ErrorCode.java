package com.app.utils;

public enum ErrorCode {

    USER_THIS_NAME_EXISTS(1000, "Пользователь с таким именем уже существует"),
    INN_THIS_NAME_EXISTS(1001, "МНН с таким рекомендованным названием уже существует"),
    FIRM_THIS_NAME_EXISTS(1002, "Фирма с таким именем уже существует"),
    ENTITY_CONTAINS_LINKS(1003, "Пока другие объекты ссылаются на текущий, он не может быть удален"),
    NOT_FIND_ENTITY(1004, "Невозможно найти объект"),
    NOT_PRICE(1005, "Невозможно найти цену на препарат");

    int code;
    String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
