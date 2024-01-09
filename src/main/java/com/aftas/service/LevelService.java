package com.aftas.service;

import com.aftas.domain.entities.Level;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LevelService {
    Level createLevel(Level level) throws ValidationException;

    List<Level> getAllLevels();
}
