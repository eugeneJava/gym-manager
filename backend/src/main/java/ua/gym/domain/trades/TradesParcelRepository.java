package ua.gym.domain.trades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradesParcelRepository extends JpaRepository<TradesParcel, String> {
    List<TradesParcel> findAllByOrderByStartedDeliveryAtDesc();

    @Query("SELECT p FROM TradesParcel p WHERE p.deliveredAt IS NULL ORDER BY p.startedDeliveryAt DESC")
    List<TradesParcel> findAllDeliveringParcels();
}