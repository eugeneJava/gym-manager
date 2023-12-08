package ua.gym.domain.tableManager;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static java.time.temporal.ChronoUnit.MINUTES;

public class TimeUtils {

    public static double timeToHours(TimeDto time) {
        double minutes = time.getMinutes();
        double min = valueOf(minutes / 60d).round(new MathContext(2, HALF_UP)).doubleValue();
        double hours = time.getHours() + min;
        return hours;
    }


    public static TimeDto sum(TimeDto time1, TimeDto time2) {
        int hours = time1.getHours() + time2.getHours();
        int minutes = time1.getMinutes() + time2.getMinutes();
        int hoursInMin = minutes / 60;
        int minutesLeft = minutes % 60;

        return new TimeDto(hours + hoursInMin, minutesLeft);
    }

    public static TimeDto duration(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        int minutes = Long.valueOf(duration.toMinutes()).intValue();
        int hoursInMin = minutes / 60;
        int minutesLeft = minutes % 60;

        return new TimeDto(hoursInMin, minutesLeft);
    }

    public static void main(String[] args) {


        TimeUtils timeUtils = new TimeUtils();
       /* double v = timeUtils.timeToHours(new TimeDto(3, 30));
        int a = 9;

        new TableSession(BigDecimal.valueOf(100), 3, new TimeDto(2, 30));*/


/*        TimeDto result = sum(new TimeDto(1, 40), new TimeDto(1, 31));
        int b = 9;

        LocalDateTime end = LocalDateTime.of(2023, 11, 11, 16, 01);
        LocalDateTime start = LocalDateTime.of(2023, 11, 11, 15, 45);
        TimeDto difference = duration(start, end);

        int o = 9;*/
        ClientSession clientSession = new ClientSession();
        new TableSession(clientSession, nowTruncatedtoMinutes(), new BigDecimal(100), 3, new TimeDto(0, 15), ZERO);

        clientSession.moveToAnotherTable(2, new BigDecimal(100));
        int i = 10;
    }

    public static LocalDateTime nowTruncatedtoMinutes() {
        return LocalDateTime.now().truncatedTo(MINUTES);
    }
}
