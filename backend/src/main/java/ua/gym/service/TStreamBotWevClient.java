package ua.gym.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.gym.ui.dtos.trades.TradesProductSaleDto;
import ua.gym.ui.dtos.trades.TradesProductSaleGroupDto;
import ua.gym.ui.internal.ParcelDeliveryInfoDto;

@Component
public class TStreamBotWevClient {
    private String botUrl = "http://bot:8081/internal-api";

    public void updateParcelDelivered(ParcelDeliveryInfoDto parcel) {
        String url = botUrl + "/parcel/sent";
        getRestTemplate().postForObject(url, parcel, ParcelDeliveryInfoDto.class);
    }

    public void updateGroupSold(TradesProductSaleGroupDto parcel) {
        String url = botUrl + "/group/sold";
        getRestTemplate().postForObject(url, parcel, ParcelDeliveryInfoDto.class);
    }

    public void updateProductSold(TradesProductSaleDto sale) {
        String url = botUrl + "/product/sold";
        getRestTemplate().postForObject(url, sale, TradesProductSaleDto.class);
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
