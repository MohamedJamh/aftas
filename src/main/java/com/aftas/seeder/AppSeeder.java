package com.aftas.seeder;

import com.aftas.seeder.dbSeeders.FishSeeder;
import com.aftas.seeder.dbSeeders.LevelSeeder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AppSeeder {
    private final FishSeeder fishSeeder;
    private final LevelSeeder levelSeeder;
    public AppSeeder(FishSeeder fishSeeder, LevelSeeder levelSeeder) {
        this.fishSeeder = fishSeeder;
        this.levelSeeder = levelSeeder;
    }
    @PostConstruct
    public void init() {
        levelSeeder.seed();
        fishSeeder.seed();
    }
}
