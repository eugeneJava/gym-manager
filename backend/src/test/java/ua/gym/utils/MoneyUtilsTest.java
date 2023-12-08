package ua.gym.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class MoneyUtilsTest {
    @Test
    public void roundToFive_13_99__15() {
        BigDecimal result = MoneyUtils.roundToFive(new BigDecimal(13.99));

        Assertions.assertThat(result).isEqualTo(BigDecimal.valueOf(15));
    }

    @Test
    public void roundToFive_11_76__15() {
        BigDecimal result = MoneyUtils.roundToFive(new BigDecimal(10.76));

        Assertions.assertThat(result).isEqualTo(BigDecimal.valueOf(15));
    }

    @Test
    public void roundToFive_9_76__10() {
        BigDecimal result = MoneyUtils.roundToFive(new BigDecimal(9.76));

        Assertions.assertThat(result).isEqualTo(BigDecimal.valueOf(10));
    }

    @Test
    public void roundToFive_10_25__10() {
        BigDecimal result = MoneyUtils.roundToFive(new BigDecimal(10.25));

        Assertions.assertThat(result).isEqualTo(BigDecimal.valueOf(10));
    }
}