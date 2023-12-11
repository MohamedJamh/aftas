package com.aftas.service;

import com.aftas.domain.Level;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public interface LevelService {
    Level createLevel(Level level) throws ValidationException;

    List<Level> getAllLevels();
}
