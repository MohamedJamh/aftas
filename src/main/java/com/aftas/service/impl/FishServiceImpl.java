package com.aftas.service.impl;

import com.aftas.domain.entities.Fish;
import com.aftas.domain.entities.Level;
import com.aftas.exception.custom.ValidationException;
import com.aftas.repository.FishRepository;
import com.aftas.repository.LevelRepository;
import com.aftas.service.FishService;
import com.aftas.utils.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class FishServiceImpl implements FishService {

    private final FishRepository fishRepository;
    private final LevelRepository levelRepository;


    public FishServiceImpl(FishRepository fishRepository, LevelRepository levelRepository) {
        this.fishRepository = fishRepository;
        this.levelRepository = levelRepository;
    }
    @Override
    public Fish createFish(Fish fish) throws ValidationException {
        Optional<Fish> fishOptional = fishRepository.findByName(fish.getName());
        if(fishOptional.isPresent()) {
            throw new ValidationException(new ErrorMessage("Fish with name already exists"));
        }
        Optional<Level> levelOptional = levelRepository.getLevelByCode(fish.getLevel().getCode());
        if(levelOptional.isEmpty())
            throw new ValidationException(new ErrorMessage("Level with code not found"));
        fish.setLevel(levelOptional.get());
        return fishRepository.save(fish);
    }

    @Override
    public List<Fish> getAllFishes() {
        return fishRepository.findAll();
    }

    @Override
    public Fish getFishIfExists(String fishName) throws ValidationException {
        Optional<Fish> fishOptional = fishRepository.findByName(fishName);
        if(fishOptional.isEmpty())
            throw new ValidationException(new ErrorMessage("Fish not found"));
        return fishOptional.get();
    }
}
