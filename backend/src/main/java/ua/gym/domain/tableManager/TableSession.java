package ua.gym.domain.tableManager;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.MoneyUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ZERO;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.nonNull;
import static ua.gym.domain.tableManager.TimeUtils.*;
import static ua.gym.utils.Assertions.assertPresent;
import static ua.gym.utils.Assertions.assertState;
import static ua.gym.utils.MoneyUtils.roundToFive;

@Entity
@Table(name = "table_session")
public class TableSession extends Identifiable {

    @ManyToOne(optional = false)
    private ClientSession clientSession;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime closeDate;
    private BigDecimal rate;
    private int tableNumber;
    private BigDecimal paidAmount = ZERO;
    private String duration;

    @Transient
    private BigDecimal totalPay = ZERO;

    TableSession() {
    }

    public TableSession(
            ClientSession clientSession,
            LocalDateTime startDate,
            BigDecimal rate,
            int tableNumber,
            TimeDto duration, BigDecimal paidAmount) {
        assertPresent(clientSession);
        assertState(rate.doubleValue() > 0, "Rate should be greater that zero");
        assertState(tableNumber > 0, "Table should be greater that zero");
        assertState(duration.getHours() > 0 || duration.getMinutes() > 0, "Duration should be greater than zero");
        this.clientSession = clientSession;
        this.clientSession.addTableSession(this);

        this.startDate = startDate.truncatedTo(MINUTES);
        this.endDate = this.startDate.plusHours(duration.getHours()).plusMinutes(duration.getMinutes());
        this.rate = rate;
        this.tableNumber = tableNumber;
        this.duration = fromTimeDto(duration);
        this.totalPay = calculateTotalPay(duration);
        this.paidAmount = nonNull(paidAmount) ? paidAmount : ZERO;
    }

    public void addTime(TimeDto time, BigDecimal paid) {
        assertState(time.getHours() > 0 || time.getMinutes() > 0, "Duration should be greater than zero");
        this.endDate = this.endDate.plusHours(time.getHours()).plusMinutes(time.getMinutes());
        this.duration = fromTimeDto(sum(toTimeDto(duration), time));
        BigDecimal additionalPay = calculateTotalPay(time);
        this.totalPay = totalPay.add(additionalPay);

        if (nonNull(paid)) {
            this.paidAmount = this.paidAmount.add(paid);
        }
    }

    public BigDecimal calculateTotalPay(LocalDateTime toDate) {
        assertState(toDate.isAfter(getStartDate()) || toDate.isEqual(getStartDate()), "Cannot calculate for tate before start date");
        LocalDateTime endDate = toDate.isBefore(getEndDate()) ? toDate : getEndDate();
        TimeDto duration = TimeUtils.duration(getStartDate(), endDate);
        return calculateTotalPay(duration);
    }

    private BigDecimal calculateTotalPay(TimeDto duration) {
        double durationInHours = timeToHours(duration);
        BigDecimal totalPay = rate.multiply(BigDecimal.valueOf(durationInHours));
        return roundToFive(totalPay);
    }

    @PostLoad
    public void initPaymentData() {
        this.totalPay = calculateTotalPay(toTimeDto(this.duration));
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

    public int getTableNumber() {
        return tableNumber;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public TimeDto getDuration() {
        return toTimeDto(this.duration);
    }

    public String getDurationAsString() {
        return this.duration;
    }

    public void close() {
        assertState(!isClosed(), "Session is already closed");
        this.closeDate = nowTruncatedtoMinutes();
    }

    public boolean isClosed() {
        return nonNull(this.closeDate);
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public BigDecimal calculateNeedToPay() {
        return roundToFive(totalPay.subtract(paidAmount));
    }

    public ClientSession getClientSession() {
        return clientSession;
    }

    private TimeDto toTimeDto(String duration) {
        String[] hoursAndMinutes = duration.split(":");
        int hours = Integer.valueOf(hoursAndMinutes[0]);
        int minutes = Integer.valueOf(hoursAndMinutes[1]);
        return new TimeDto(hours, minutes);
    }

    private String fromTimeDto(TimeDto duration) {
        String hours = String.format("%02d", duration.getHours());
        String minutes = String.format("%02d", duration.getMinutes());
        return hours + ":" + minutes;
    }
}
