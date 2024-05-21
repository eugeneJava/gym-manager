package ua.gym.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.gym.ui.dtos.trades.ProductTradeStatisticsDto;
import ua.gym.ui.internal.ParcelDeliveryInfoDto;

@Component
public class TStreamBotWevClient {

    public void updateParcelDelivered(ParcelDeliveryInfoDto parcel) {
        String url = "http://localhost:8081/internal-api/parcel/sent";
        getRestTemplate().postForObject(url, parcel, ParcelDeliveryInfoDto.class);
    }


    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
