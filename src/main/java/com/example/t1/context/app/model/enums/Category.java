package com.example.t1.context.app.model.enums;

public enum Category {

    LANGUAGES_FRAMEWORKS("LANGUAGES & FRAMEWORKS"),
    TECHNIQUES_TOOLS("TECHNIQUES & TOOLS"),
    PLATFORMS_INFRASTRUCTURE("PLATFORMS & INFRASTRUCTURE"),
    DATA_MANAGEMENT("DATA MANAGEMENT");

    private final String category;

    Category(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }


}
