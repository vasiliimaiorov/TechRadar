package com.example.t1.context.app.model.enums;

public enum Status {
    NEW("NEW"),
    MOVED_UP("MOVED_UP"),
    MOVED_DOWN("MOVED_DOWN");

    private final String status;

    Status(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }
}
