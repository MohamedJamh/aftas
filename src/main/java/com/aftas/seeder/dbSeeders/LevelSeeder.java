package com.aftas.seeder.dbSeeders;

import com.aftas.domain.Fish;
import com.aftas.domain.Level;
import com.aftas.repository.FishRepository;
import com.aftas.repository.LevelRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LevelSeeder {
    private final LevelRepository levelRepository;

    private final Level[] levels = {
            Level.builder().code(1).points(50).description("Just starting out! Catch easy-to-find and smaller fishes.").build(),
            Level.builder().code(2).points(100).description("Progressing well. Aim for a variety of medium-sized fishes.").build(),
            Level.builder().code(3).points(200).description("Demonstrating proficiency. Challenge yourself with larger and more challenging catches.").build(),
            Level.builder().code(4).points(400).description("A seasoned angler. Go after specific species and explore different fishing techniques.").build(),
            Level.builder().code(5).points(800).description("Mastering the art of fishing. Pursue the biggest and most elusive ocean dwellers.").build(),
            Level.builder().code(6).points(1500).description("Attained legendary status. Share your knowledge and skills with other fishing enthusiasts.").build(),
            Level.builder().code(7).points(2500).description("Among the elite. Hunt challenging species in deep waters.").build(),
            Level.builder().code(8).points(4000).description("Unravel the mysteries of the ocean. Encounter rare and mystical sea creatures.").build(),
            Level.builder().code(9).points(6000).description("Conquer the vast expanse of the ocean. Seek out legendary and elusive marine species.").build(),
            Level.builder().code(10).points(7500).description("A hunter of titanic proportions. Pursue the largest and most legendary oceanic trophies.").build()
    };

    private LevelSeeder(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    private void log(){
        System.out.println("----------------------Level Seeder----------------------");
    }

    public void seed() {
        this.log();
        if(levelRepository.findAll().isEmpty())
            levelRepository.saveAll(Arrays.stream(levels).toList());
    }
}
