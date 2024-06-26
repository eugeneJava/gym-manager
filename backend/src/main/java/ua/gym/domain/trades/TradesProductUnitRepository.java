package ua.gym.domain.trades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradesProductUnitRepository extends JpaRepository<TradesProductUnit, String> {

    @Query("SELECT unit FROM TradesProductUnit unit " +
            " JOIN unit.productBuy buy" +
            " LEFT JOIN buy.parcelGroup parcelGroup" +
            " LEFT JOIN parcelGroup.parcel parcel " +

            "WHERE (parcelGroup IS NULL OR (parcel IS NOT NULL AND parcel.deliveredAt IS NOT NULL)) AND unit.productSale IS NULL and unit.notAvailabityReason IS NULL")
    List<TradesProductUnit> getAvailableForSaleProductUnits();


    @Query("SELECT unit FROM TradesProductUnit unit " +
            " JOIN unit.productBuy buy" +
            " LEFT JOIN buy.parcelGroup parcelGroup" +
            " LEFT JOIN parcelGroup.parcel parcel " +

            "WHERE (parcelGroup IS NULL OR (parcel IS NOT NULL AND parcel.deliveredAt IS NOT NULL)) " +
            " AND unit.productSale IS NULL AND unit.notAvailabityReason IS NULL AND unit.product = :product")
    List<TradesProductUnit> getAvailableForSaleProductUnits(TradesProduct product);

}
