package ua.gym.service;

import org.springframework.stereotype.Component;
import ua.gym.domain.trades.DeliveryType;
import ua.gym.domain.trades.TradesParcel;

import java.time.LocalDate;

@Component
public class DeliveryDurationProvider {

    public LocalDate calculateApproximateDeliveryDate(TradesParcel parcel) {
        if (parcel.getStartedDeliveryAt() == null) {
            return null;
        }

        if (parcel.getDeliveryType().equals(DeliveryType.SEA)) {
            return parcel.getStartedDeliveryAt().plusDays(65);
        }

        if (parcel.getDeliveryType().equals(DeliveryType.AVIA)) {
            return parcel.getStartedDeliveryAt().plusDays(16);
        }

        throw new IllegalArgumentException("Unknown delivery type");

    }
}
