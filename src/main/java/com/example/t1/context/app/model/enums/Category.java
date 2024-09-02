package com.example.t1.context.app.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    LANGUAGES,
    TOOLS,
    TECHNIQUES,
    PLATFORMS;

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }

}
