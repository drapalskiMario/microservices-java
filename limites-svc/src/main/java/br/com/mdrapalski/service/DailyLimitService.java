package br.com.mdrapalski.service;

import br.com.mdrapalski.entities.DailyLimit;
import br.com.mdrapalski.controller.repository.DailyLimitRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DailyLimitService {

    private DailyLimitRepository dailyLimitRepository;

    public DailyLimitService(DailyLimitRepository dailyLimitRepository) {
        this.dailyLimitRepository = dailyLimitRepository;
    }

    public Optional<DailyLimit> findById(Long id) {
        return this.dailyLimitRepository.findById(id);
    }
}
