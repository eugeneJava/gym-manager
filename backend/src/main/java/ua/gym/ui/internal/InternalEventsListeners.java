package ua.gym.ui.internal;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import ua.gym.service.TStreamBotWevClient;

@Component
public class InternalEventsListeners {
    private final TStreamBotWevClient tStreamBotWevClient;

    public InternalEventsListeners(TStreamBotWevClient tStreamBotWevClient) {
        this.tStreamBotWevClient = tStreamBotWevClient;
    }

    @TransactionalEventListener
    public void onParcelSent(ParcelSentApplicationEvent parcel) {
        tStreamBotWevClient.updateParcelDelivered(parcel.getParcel());
    }
}
