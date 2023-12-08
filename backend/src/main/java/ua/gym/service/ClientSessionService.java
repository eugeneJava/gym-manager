package ua.gym.service;

import org.springframework.stereotype.Service;
import ua.gym.domain.tableManager.TableSession;
import ua.gym.ui.dtos.TableSessionDto;
import ua.gym.utils.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ua.gym.utils.MoneyUtils.roundToFive;

@Service
public class ClientSessionService {

   public TableSessionDto mapTableSession(TableSession tableSession, LocalDateTime toDate) {
      TableSessionDto dto = new TableSessionDto(tableSession);
      BigDecimal totalPay = tableSession.calculateTotalPay(toDate);
      dto.setTotalPay(totalPay);
      dto.setNeedToPay(roundToFive(totalPay.subtract(tableSession.getPaidAmount())));
      return dto;
   }
}
