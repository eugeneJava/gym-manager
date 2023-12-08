package ua.gym.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ZERO;

public class MoneyUtils {
    public static BigDecimal FIVE = BigDecimal.valueOf(5);

    public static BigDecimal roundToFive(BigDecimal value) {
        BigDecimal rounded = roundToInteger(value);

        BigDecimal remainder = rounded.remainder(FIVE);

        if (isGreaterThan(remainder, ZERO)) {
            return rounded.subtract(remainder).add(FIVE);
        }

        return rounded;
    }

    public static boolean isGreaterThan(BigDecimal v1, BigDecimal v2) {
            return v1.compareTo(v2) > 0;
    }

    public static boolean isGreaterOrEquals(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) >= 0;
    }

    public static boolean isLessThan(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) < 0;
    }

    public static boolean isLessOrEquals(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) < 0;
    }

    public static BigDecimal roundToInteger(BigDecimal value) {
        return value.setScale(0, RoundingMode.HALF_UP);
    }

    public static boolean isEqual(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) == 0;
    }
}
