package com.aftas.service.impl;

import com.aftas.domain.entities.Level;
import com.aftas.exception.custom.ValidationException;
import com.aftas.repository.LevelRepository;
import com.aftas.service.LevelService;
import com.aftas.utils.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }
    @Override
    public Level createLevel(Level level) throws ValidationException {
        if(levelRepository.getLevelByCode(level.getCode()).isPresent())
            throw new ValidationException(new ErrorMessage("Level Code already exists"));
        if(levelRepository.getLevelByPoints(level.getPoints()).isPresent())
            throw new ValidationException(new ErrorMessage("Leve Points already exists"));
        Integer levelMaxPointAndCodeUnderLevelCode = levelRepository.findMaxPointUnderLevelCode(level.getCode());
        if(level.getPoints() < levelMaxPointAndCodeUnderLevelCode)
            throw new ValidationException(new ErrorMessage("Points must be greater than " + levelMaxPointAndCodeUnderLevelCode + " for this level code"));
        return levelRepository.save(level);
    }

    @Override
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }
}
