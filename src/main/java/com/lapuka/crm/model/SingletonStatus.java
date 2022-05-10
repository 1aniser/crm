package com.lapuka.crm.model;

public enum SingletonStatus {
    NEW("Новая"), REJECTED("Отказано"), COMPLETING("На выполнении"), COMPLETED("Готов");
    private String status;
    SingletonStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
