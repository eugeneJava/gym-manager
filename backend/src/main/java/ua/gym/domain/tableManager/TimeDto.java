package ua.gym.domain.tableManager;

import java.util.Objects;

public class TimeDto {
    private int hours;
    private int minutes;

    public TimeDto(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeDto timeDto = (TimeDto) o;
        return hours == timeDto.hours && minutes == timeDto.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }
}
