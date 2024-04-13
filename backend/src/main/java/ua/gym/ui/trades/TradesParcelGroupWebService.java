package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.TradesParcelGroup;
import ua.gym.domain.trades.TradesParcelGroupRepository;
import ua.gym.ui.dtos.trades.TradesParcelGroupDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TradesParcelGroupWebService {

    private final TradesParcelGroupRepository repository;

    @Autowired
    public TradesParcelGroupWebService(TradesParcelGroupRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/trades/parcelGroup")
    @Transactional(readOnly = true)
    public List<TradesParcelGroupDto> getAllTradesParcelGroups() {
        return repository.findAll().stream().map(TradesParcelGroupDto::new).collect(Collectors.toList());
    }

    @GetMapping("/trades/parcelGroup/{id}")
    @Transactional(readOnly = true)
    public TradesParcelGroupDto getTradesParcelGroup(@PathVariable String id) {
        TradesParcelGroup parcelGroup = repository.findById(id).orElseThrow();
        return new TradesParcelGroupDto(parcelGroup);
    }

    @PostMapping("/trades/parcelGroup")
    @Transactional
    public TradesParcelGroupDto createTradesParcelGroup(@RequestBody TradesParcelGroupDto dto) {
        return null;
    }

    @PutMapping("/trades/parcelGroup/{id}")
    @Transactional
    public TradesParcelGroupDto updateTradesParcelGroup(@PathVariable String id, @RequestBody TradesParcelGroupDto dto) {
        TradesParcelGroup parcelGroup = repository.findById(id).orElseThrow();
        // Update entity fields here
        parcelGroup = repository.save(parcelGroup);
        return new TradesParcelGroupDto(parcelGroup);
    }

    @GetMapping("/trades/parcelGroup/withoutParcel")
    @Transactional(readOnly = true)
    public List<TradesParcelGroupDto> getTradesParcelGroupsWithoutParcel() {
        return repository.findByParcelIsNull().stream().map(TradesParcelGroupDto::new).collect(Collectors.toList());
    }
}
