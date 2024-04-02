package com.nagarro.driven.project.espo.test.pageobjects.utils;

public enum CreditCards {

    VISA_CLASSIC_NL("VISA Classic NL", "4111 1111 4555 1142", "0330", "737"),
    VISA_ES("VISA Classic ES", "4988 4388 4388 4305", "0330", "737"),
    VISA_CONSUMER_NL("VISA Consumer", "4111 1111 1111 1111", "0330", "737"),
    INVALID_CARD("Invalid VISA", "0000 0000 0000 0000", "0221", "000"),
    MASTER_CARD_CONSUMER_GB("Master Card Consumer", "5555 4444 3333 1111", "0330", "737"),
    MASTER_CARD_CREDIT_NL("Master Card Credit", "2223 0000 4841 0010", "0330", "737");


    private String name;
    private String number;
    private String cvs;
    private String expirationDate;

    CreditCards(String name, String number, String expirationDate, String cvs) {
        this.name = name;
        this.number = number;
        this.cvs = cvs;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
    public String getCvs() {
        return cvs;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}
