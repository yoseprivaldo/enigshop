package com.enigmacamp.enigshop.constant;

public enum TransactionStatus {
    ORDERED("ordered", "Ordered"),
    SETTLEMENT("settlement", "Settlement"),
    PENDING("pending", "Pending"),
    CANCEL("cancel", "Cancel"),
    FAILURE("failure", "Failure"),
    EXPIRE("expire", "Expire"),
    DENY("deny", "Deny"),
    SUCCEEDED("succeeded", "Succeeded");

    private final String name;
    private final String description;

    TransactionStatus(String name, String description){
        this.name = name;
        this.description = description;
    }
}
