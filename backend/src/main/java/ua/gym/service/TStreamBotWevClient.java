package ua.gym.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.gym.ui.dtos.trades.TradesProductSaleGroupDto;
import ua.gym.ui.internal.ParcelDeliveryInfoDto;

@Component
public class TStreamBotWevClient {
    private String botUrl = "http://localhost:8081/internal-api";

    public void updateParcelDelivered(ParcelDeliveryInfoDto parcel) {
        String url = botUrl + "/parcel/sent";
        getRestTemplate().postForObject(url, parcel, ParcelDeliveryInfoDto.class);
    }

    public void updateProductSold(TradesProductSaleGroupDto parcel) {
        String url = botUrl + "/group/sold";
        getRestTemplate().postForObject(url, parcel, ParcelDeliveryInfoDto.class);
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
