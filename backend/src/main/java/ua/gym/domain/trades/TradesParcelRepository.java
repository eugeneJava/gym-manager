package ua.gym.domain.trades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradesParcelRepository extends JpaRepository<TradesParcel, String> {
    List<TradesParcel> findAllByOrderByStartedDeliveryAtDesc();
}