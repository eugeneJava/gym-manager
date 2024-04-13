package ua.gym.domain.trades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradesProductRepository extends JpaRepository<TradesProduct, String> {
    List<TradesProduct> findAllByOrderByNameAsc();
}
