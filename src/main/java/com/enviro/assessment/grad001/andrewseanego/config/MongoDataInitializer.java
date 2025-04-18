package com.enviro.assessment.grad001.andrewseanego.config;

import com.enviro.assessment.grad001.andrewseanego.entity.ERole;
import com.enviro.assessment.grad001.andrewseanego.entity.Role;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.repository.RoleRepository;
import com.enviro.assessment.grad001.andrewseanego.repository.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MongoDataInitializer {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public CommandLineRunner initDatabase(RoleRepository roleRepository, 
                                         WasteCategoryRepository wasteCategoryRepository) {
        return args -> {
            // Initialize roles if they don't exist
            if (roleRepository.count() == 0) {
                Role userRole = new Role(ERole.ROLE_USER);
                Role modRole = new Role(ERole.ROLE_MODERATOR);
                Role adminRole = new Role(ERole.ROLE_ADMIN);
                
                roleRepository.saveAll(Arrays.asList(userRole, modRole, adminRole));
                System.out.println("Roles initialized");
            }
            
            // Initialize waste categories if they don't exist
            if (wasteCategoryRepository.count() == 0) {
                WasteCategory plastic = new WasteCategory();
                plastic.setName("Plastic");
                plastic.setDescription("Recyclable plastic materials");
                plastic.setRecyclable(true);
                plastic.setDisposalMethods(List.of("Landfill, Recycling plant"));
                
                WasteCategory paper = new WasteCategory();
                paper.setName("Paper");
                paper.setDescription("Paper and cardboard materials");
                paper.setRecyclable(true);
                paper.setDisposalMethods(List.of("Recycling plant, Composting"));
                
                WasteCategory glass = new WasteCategory();
                glass.setName("Glass");
                glass.setDescription("Glass bottles and containers");
                glass.setRecyclable(true);
                glass.setDisposalMethods(List.of("Recycling plant"));
                
                WasteCategory metal = new WasteCategory();
                metal.setName("Metal");
                metal.setDescription("Metal cans and containers");
                metal.setRecyclable(true);
                metal.setDisposalMethods(List.of("Recycling plant, Scrap yard"));
                
                WasteCategory organic = new WasteCategory();
                organic.setName("Organic");
                organic.setDescription("Food waste and yard trimmings");
                organic.setRecyclable(true);
                organic.setDisposalMethods(List.of("Composting, Anaerobic digestion"));
                
                WasteCategory electronic = new WasteCategory();
                electronic.setName("Electronic");
                electronic.setDescription("Electronic devices and components");
                electronic.setRecyclable(true);
                electronic.setDisposalMethods(List.of("E-waste recycling center"));
                
                WasteCategory hazardous = new WasteCategory();
                hazardous.setName("Hazardous");
                hazardous.setDescription("Chemicals, batteries, and other hazardous materials");
                hazardous.setRecyclable(false);
                hazardous.setDisposalMethods(List.of("Hazardous waste facility"));
                
                wasteCategoryRepository.saveAll(Arrays.asList(plastic, paper, glass, metal, organic, electronic, hazardous));
                System.out.println("Waste categories initialized");
            }
        };
    }
}
