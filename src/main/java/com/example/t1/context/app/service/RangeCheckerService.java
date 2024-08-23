package com.example.t1.context.app.service;

import org.springframework.stereotype.Service;

@Service
public class RangeCheckerService {

    public int determineRange(double number) {
        int diapasonsNum = 4;
        int max = 15;

        double rangeSize = (double) max / diapasonsNum;
        return (int) Math.floor((max - number) / rangeSize);
    }
}
