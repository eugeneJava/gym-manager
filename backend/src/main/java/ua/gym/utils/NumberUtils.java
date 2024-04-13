package ua.gym.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class NumberUtils {
    public static BigDecimal v(int value) {
        return new BigDecimal(value);
    }

    public static BigDecimal HUNDRED = BigDecimal.valueOf(100);

    public static BigDecimal multiply(BigDecimal multiplicand1, BigDecimal multiplicand2) {
        return multiplicand1.multiply(multiplicand2).setScale(2, HALF_UP);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor,2, HALF_UP);
    }

    public static boolean greaterThanZero(BigDecimal value) {
        return value.compareTo(ZERO) > 0;
    }

    public static boolean isZero(BigDecimal value) {
        return value.compareTo(ZERO) == 0;
    }

    public static boolean greaterOrEqualZero(BigDecimal value) {
        return value.compareTo(ZERO) >= 0;
    }

    public static BigDecimal percenage(BigDecimal value, BigDecimal valueFrom) {
        if (isZero(value)) {
            return ZERO;
        }
        return value.abs().divide(valueFrom.abs(),2, HALF_UP).multiply(HUNDRED).setScale(2, HALF_UP).subtract(HUNDRED);
    }

    public static int compare(BigDecimal value1, BigDecimal value2) {
        if (nonNull(value1)) {
            return isNull(value2) ? 1 : value1.compareTo(value2);
        }

        if (nonNull(value2)) {
            return isNull(value1) ? 1 : value1.compareTo(value2);
        }
        return 0;
    }

    public static BigDecimal diffInPercent(BigDecimal value, BigDecimal valueFrom) {
        BigDecimal divide = value.divide(valueFrom, 10, RoundingMode.HALF_UP);
        BigDecimal diffInPercent = HUNDRED.subtract(divide.multiply(HUNDRED));
        return diffInPercent;
    }
}
