package ua.gym.domain.trades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradesProductBuyRepository extends JpaRepository<TradesProductBuy, String> {
    List<TradesProductBuy> findAllByProduct(TradesProduct product);

    @Query("SELECT buy FROM TradesProductBuy buy " +
            " LEFT JOIN buy.parcelGroup parcelGroup" +
            " LEFT JOIN parcelGroup.parcel parcel " +

            "WHERE parcelGroup IS NULL OR (parcelGroup IS NOT NULL AND parcel IS NOT NULL) ORDER BY buy.purchaseDate DESC ")
    List<TradesProductBuy> findAllBuysWithCalculatedPrice();

    List<TradesProductBuy> findAllByOrderByPurchaseDateDesc();
}
