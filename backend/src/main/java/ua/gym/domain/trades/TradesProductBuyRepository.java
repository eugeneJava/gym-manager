package ua.gym.domain.trades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradesProductBuyRepository extends JpaRepository<TradesProductBuy, String> {
    List<TradesProductBuy> findAllByProduct(TradesProduct product);
}
