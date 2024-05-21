package ua.gym.ui.internal;

public class ParcelSentApplicationEvent {
    private ParcelDeliveryInfoDto parcel;

    public ParcelSentApplicationEvent(ParcelDeliveryInfoDto parcel) {
        this.parcel = parcel;
    }

    public ParcelDeliveryInfoDto getParcel() {
        return parcel;
    }
}
