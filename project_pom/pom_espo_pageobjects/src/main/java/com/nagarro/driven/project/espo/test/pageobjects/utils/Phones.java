package com.nagarro.driven.project.espo.test.pageobjects.utils;

public enum Phones {

    PHONE_RO("+40734556789"),
    PHONE_INVALID_RO("+40222"),
    PHONE_NW("40034567"),
    PHONE_NO_INVALID("111111");

    private String number;

    Phones(String number) {
        this.number = number;
    }

    public String getNumber() {return number;
    }
}
