package com.example.t1.context.app.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    MOVED_DOWN(-1),
    MOVED_UP(1),
    NEW(2);

    private final Integer num;

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }

}
