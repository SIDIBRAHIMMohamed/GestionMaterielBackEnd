/**
 * Represents client config class
 */

package com.project.Materiel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MaterielConfig {
    /**
     * Insert come data
     * @param materielRepository MaterielRepository
     * @return CommandLineRunner
     */
    @Bean
    CommandLineRunner insertUsers(MaterielRepository materielRepository) {
        return args -> {
            // Create Materiel
            Materiel materiel = new Materiel("Laptop", "2022", "ABC123", 0);
            // Insert :
            materielRepository.save(materiel);
        };
    }

}
