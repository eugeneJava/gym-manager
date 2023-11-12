package ua.gym.domain.tableManager;

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
}
