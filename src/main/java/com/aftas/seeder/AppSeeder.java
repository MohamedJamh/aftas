package com.aftas.seeder;

import com.aftas.seeder.dbSeeders.FishSeeder;
import com.aftas.seeder.dbSeeders.LevelSeeder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppSeeder {
    private final FishSeeder fishSeeder;
    private final LevelSeeder levelSeeder;
    @Value("${seeders.enabled}")
    private Boolean seed;
    public AppSeeder(FishSeeder fishSeeder, LevelSeeder levelSeeder) {
        this.fishSeeder = fishSeeder;
        this.levelSeeder = levelSeeder;
    }
    @PostConstruct
    public void init() {
        if(Boolean.FALSE.equals(seed)) return;
        levelSeeder.seed();
        fishSeeder.seed();
    }
}
