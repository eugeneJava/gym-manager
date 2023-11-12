package ua.gym.domain.tableManager;

import ua.gym.persistense.Identifiable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ZERO;
import static java.time.temporal.ChronoUnit.MINUTES;
import static ua.gym.domain.tableManager.TimeUtils.sum;
import static ua.gym.domain.tableManager.TimeUtils.timeToHours;
import static ua.gym.utils.Assertions.*;

@Entity
@Table
public class TableSession extends Identifiable {

    @ManyToOne(optional = false)
    private ClientSession clientSession;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime closeDate;
    private BigDecimal rate;
    private int table;

    private BigDecimal totalPay = ZERO;
    private BigDecimal paid = ZERO;

    @Transient
    private TimeDto duration;

    TableSession(
            ClientSession clientSession,
            BigDecimal rate,
            int table,
            TimeDto duration, boolean paid) {
        assertPresent(clientSession);
        assertState(rate.doubleValue() > 0, "Rate should be greater that zero");
        assertState(table > 0, "Table should be greater that zero");
        assertState(duration.getHours() > 0 || duration.getMinutes() > 0, "Duration should be greater than zero");
        this.clientSession = clientSession;
        this.clientSession.addTableSession(this);

        LocalDateTime now = now();
        this.startDate = now;
        this.endDate = this.startDate.plusHours(duration.getHours()).plusMinutes(duration.getMinutes());
        this.rate = rate;
        this.table = table;
        this.duration = duration;
        this.totalPay = calculateTotalPay(duration);
        if (paid) {
            this.paid = totalPay;
        }
    }

    public void addTime(TimeDto time, boolean paid) {
        assertState(time.getHours() > 0 || time.getMinutes() > 0, "Duration should be greater than zero");
        this.endDate = this.endDate.plusHours(time.getHours()).plusMinutes(time.getMinutes());
        this.duration = sum(duration, time);
        BigDecimal additionalPay = calculateTotalPay(time);
        this.totalPay = totalPay.add(additionalPay);

        if (paid) {
            this.paid = this.paid.add(additionalPay);
        }
    }

    private BigDecimal calculateTotalPay(TimeDto duration) {
        double durationInHours = timeToHours(duration);
        BigDecimal totalPay = rate.multiply(BigDecimal.valueOf(durationInHours));
        return totalPay;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public int getTable() {
        return table;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public TimeDto getDuration() {
        return new TimeDto(duration.getHours(), duration.getMinutes());
    }

    void close() {
        this.closeDate = now();
    }


    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    private LocalDateTime now() {
        return LocalDateTime.now().truncatedTo(MINUTES);
    }

    public BigDecimal calculateNeedToPay() {
        return totalPay.subtract(paid);
    }
}
