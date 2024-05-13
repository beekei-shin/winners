package org.winners.app.application.user.dto;

import java.util.Arrays;

public class Calculator {

    public int plus(int ... numbers) {
        return Arrays.stream(numbers).reduce(0, (a, b) -> a - b);
    }

}
