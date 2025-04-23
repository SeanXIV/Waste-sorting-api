package com.enviro.assessment.grad001.andrewseanego.config;

import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
import com.enviro.assessment.grad001.andrewseanego.entity.ERole;
import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import com.enviro.assessment.grad001.andrewseanego.entity.Role;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.repository.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.andrewseanego.repository.RecyclingTipRepository;
import com.enviro.assessment.grad001.andrewseanego.repository.RoleRepository;
import com.enviro.assessment.grad001.andrewseanego.repository.WasteCategoryRepository;
import com.mongodb.MongoException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MongoDataInitializer {

    // No need for MongoTemplate as we're using repositories directly

    @Bean
    public CommandLineRunner initDatabase(RoleRepository roleRepository,
                                         WasteCategoryRepository wasteCategoryRepository,
                                         DisposalGuidelineRepository disposalGuidelineRepository,
                                         RecyclingTipRepository recyclingTipRepository) {
        return args -> {
            try {
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

                // Save waste categories first and get the saved instances
                List<WasteCategory> savedCategories = wasteCategoryRepository.saveAll(Arrays.asList(plastic, paper, glass, metal, organic, electronic, hazardous));
                System.out.println("Waste categories initialized");

                // Get the saved categories by name for reference
                WasteCategory savedPlastic = wasteCategoryRepository.findByNameIgnoreCase("Plastic").orElse(plastic);
                WasteCategory savedPaper = wasteCategoryRepository.findByNameIgnoreCase("Paper").orElse(paper);
                WasteCategory savedGlass = wasteCategoryRepository.findByNameIgnoreCase("Glass").orElse(glass);
                WasteCategory savedMetal = wasteCategoryRepository.findByNameIgnoreCase("Metal").orElse(metal);
                WasteCategory savedOrganic = wasteCategoryRepository.findByNameIgnoreCase("Organic").orElse(organic);
                WasteCategory savedElectronic = wasteCategoryRepository.findByNameIgnoreCase("Electronic").orElse(electronic);
                WasteCategory savedHazardous = wasteCategoryRepository.findByNameIgnoreCase("Hazardous").orElse(hazardous);

                // Initialize disposal guidelines if they don't exist
                if (disposalGuidelineRepository.count() == 0) {
                    // Plastic disposal guidelines
                    DisposalGuideline plasticGuideline = new DisposalGuideline();
                    plasticGuideline.setWasteCategory(savedPlastic);
                    plasticGuideline.setDescription("Proper disposal of plastic waste");
                    plasticGuideline.setSteps("1. Rinse containers to remove food residue\n2. Remove caps and lids\n3. Flatten bottles to save space\n4. Sort by plastic type (check recycling numbers)\n5. Place in designated recycling bins");
                    plasticGuideline.setLegalRequirements("Many municipalities have regulations requiring separation of recyclable plastics. Some regions have banned single-use plastics entirely.");

                    // Paper disposal guidelines
                    DisposalGuideline paperGuideline = new DisposalGuideline();
                    paperGuideline.setWasteCategory(savedPaper);
                    paperGuideline.setDescription("Eco-friendly paper waste disposal");
                    paperGuideline.setSteps("1. Remove any plastic wrapping or non-paper materials\n2. Separate glossy magazines from regular paper\n3. Shred confidential documents\n4. Bundle newspapers and cardboard separately\n5. Keep paper dry to maintain recyclability");
                    paperGuideline.setLegalRequirements("Commercial entities often have mandatory paper recycling requirements. Secure disposal of confidential documents may be legally required for certain businesses.");

                    // Glass disposal guidelines
                    DisposalGuideline glassGuideline = new DisposalGuideline();
                    glassGuideline.setWasteCategory(savedGlass);
                    glassGuideline.setDescription("Safe and effective glass recycling");
                    glassGuideline.setSteps("1. Rinse thoroughly to remove contents\n2. Remove metal or plastic lids\n3. Sort by color (clear, green, brown)\n4. Handle broken glass with care - wrap in paper before disposal\n5. Use designated glass recycling containers");
                    glassGuideline.setLegalRequirements("Some regions require glass to be sorted by color. Broken glass may need special handling procedures for safety reasons.");

                    // Metal disposal guidelines
                    DisposalGuideline metalGuideline = new DisposalGuideline();
                    metalGuideline.setWasteCategory(savedMetal);
                    metalGuideline.setDescription("Maximizing metal recycling value");
                    metalGuideline.setSteps("1. Rinse food containers thoroughly\n2. Remove paper labels when possible\n3. Separate different types of metals (aluminum, steel, etc.)\n4. Crush cans to save space\n5. Consider taking valuable metals to scrap yards for compensation");
                    metalGuideline.setLegalRequirements("Certain metals like lead and mercury have strict disposal regulations. Scrap metal businesses may require ID and documentation for certain metal sales to prevent theft.");

                    // Organic disposal guidelines
                    DisposalGuideline organicGuideline = new DisposalGuideline();
                    organicGuideline.setWasteCategory(savedOrganic);
                    organicGuideline.setDescription("Turning food waste into garden gold");
                    organicGuideline.setSteps("1. Separate food scraps from other waste\n2. Avoid including meat, dairy, and oils in home composting\n3. Layer with brown materials (leaves, paper) in compost bins\n4. Turn compost regularly for aeration\n5. Use finished compost in gardens to complete the cycle");
                    organicGuideline.setLegalRequirements("Some municipalities have banned food waste from landfills. Commercial composting may require permits. Home composting may have regulations in urban areas.");

                    // Electronic disposal guidelines
                    DisposalGuideline electronicGuideline = new DisposalGuideline();
                    electronicGuideline.setWasteCategory(savedElectronic);
                    electronicGuideline.setDescription("Responsible e-waste management");
                    electronicGuideline.setSteps("1. Back up and securely erase personal data\n2. Remove batteries for separate recycling\n3. Never throw electronics in regular trash\n4. Take to designated e-waste collection centers\n5. Consider manufacturer take-back programs for newer devices");
                    electronicGuideline.setLegalRequirements("Many regions have banned e-waste from landfills. Businesses may have legal requirements for data destruction. Some electronics contain hazardous materials with special disposal regulations.");

                    // Hazardous waste disposal guidelines
                    DisposalGuideline hazardousGuideline = new DisposalGuideline();
                    hazardousGuideline.setWasteCategory(savedHazardous);
                    hazardousGuideline.setDescription("Safely handling dangerous materials");
                    hazardousGuideline.setSteps("1. Keep in original labeled containers when possible\n2. Never mix different hazardous materials\n3. Store in cool, dry place away from children and pets\n4. Transport securely to prevent spills\n5. Only take to designated hazardous waste facilities during collection events");
                    hazardousGuideline.setLegalRequirements("Strict regulations govern hazardous waste disposal. Improper disposal can result in significant fines. Businesses generating hazardous waste must follow specific documentation and disposal procedures.");

                    disposalGuidelineRepository.saveAll(Arrays.asList(
                        plasticGuideline, paperGuideline, glassGuideline, metalGuideline,
                        organicGuideline, electronicGuideline, hazardousGuideline
                    ));
                    System.out.println("Disposal guidelines initialized");
                }

                // Initialize recycling tips if they don't exist
                if (recyclingTipRepository.count() == 0) {
                    // Plastic recycling tips
                    RecyclingTip plasticTip1 = new RecyclingTip();
                    plasticTip1.setWasteCategory(savedPlastic);
                    plasticTip1.setTitle("The Plastic Number System Decoded");
                    plasticTip1.setDescription("Look for the number inside the recycling symbol on plastic items. #1 (PET) and #2 (HDPE) are most widely recyclable. #3 (PVC) and #6 (PS) are often problematic and may not be accepted in curbside programs. Always check local guidelines.");
                    plasticTip1.setSource("Environmental Protection Agency");

                    RecyclingTip plasticTip2 = new RecyclingTip();
                    plasticTip2.setWasteCategory(savedPlastic);
                    plasticTip2.setTitle("Plastic Bag Alternatives");
                    plasticTip2.setDescription("Most curbside programs don't accept plastic bags as they jam sorting machinery. Instead, collect clean, dry bags and return to grocery store collection points. Better yet, switch to reusable shopping bags, which can each replace hundreds of single-use bags over their lifetime.");
                    plasticTip2.setSource("Plastic Pollution Coalition");

                    // Paper recycling tips
                    RecyclingTip paperTip1 = new RecyclingTip();
                    paperTip1.setWasteCategory(savedPaper);
                    paperTip1.setTitle("Paper Recycling Water Savings");
                    paperTip1.setDescription("Recycling one ton of paper saves approximately 7,000 gallons of water compared to making new paper from trees. It also saves 3.3 cubic yards of landfill space and reduces greenhouse gas emissions by one metric ton of carbon equivalent.");
                    paperTip1.setSource("American Forest & Paper Association");

                    RecyclingTip paperTip2 = new RecyclingTip();
                    paperTip2.setWasteCategory(savedPaper);
                    paperTip2.setTitle("Pizza Box Recycling Myth");
                    paperTip2.setDescription("Contrary to popular belief, pizza boxes with minor grease stains can often be recycled. Cut off heavily soiled portions and recycle the clean parts. Some facilities now accept the entire box as their processes can handle small amounts of food residue. Check your local guidelines.");
                    paperTip2.setSource("Waste Management Sustainability Services");

                    // Glass recycling tips
                    RecyclingTip glassTip1 = new RecyclingTip();
                    glassTip1.setWasteCategory(savedGlass);
                    glassTip1.setTitle("Infinite Recyclability");
                    glassTip1.setDescription("Unlike many materials, glass can be recycled indefinitely without loss of quality or purity. A recycled glass bottle can be back on the store shelf in as little as 30 days after collection, saving energy and raw materials with each cycle.");
                    glassTip1.setSource("Glass Packaging Institute");

                    RecyclingTip glassTip2 = new RecyclingTip();
                    glassTip2.setWasteCategory(savedGlass);
                    glassTip2.setTitle("Beyond the Bin: Creative Glass Reuse");
                    glassTip2.setDescription("Before recycling, consider reusing glass jars for food storage, craft projects, or as vases. When recycling, remember that drinking glasses, window glass, and ovenware are made of different formulations than bottles and jars and typically cannot be recycled together.");
                    glassTip2.setSource("Zero Waste Alliance");

                    // Metal recycling tips
                    RecyclingTip metalTip1 = new RecyclingTip();
                    metalTip1.setWasteCategory(savedMetal);
                    metalTip1.setTitle("Aluminum Energy Savings");
                    metalTip1.setDescription("Recycling aluminum uses 95% less energy than producing new aluminum from raw materials. A recycled aluminum can saves enough energy to run a television for three hours. Keep the cycle going by ensuring your cans make it to the recycling bin.");
                    metalTip1.setSource("The Aluminum Association");

                    RecyclingTip metalTip2 = new RecyclingTip();
                    metalTip2.setWasteCategory(savedMetal);
                    metalTip2.setTitle("The Magnet Test");
                    metalTip2.setDescription("Not sure if a metal item is aluminum or steel? Try the magnet test. Magnets stick to steel but not to aluminum. Steel cans often have a seam on the side and bottom, while aluminum cans have a seamless bottom. Both are valuable recyclables but may be sorted differently.");
                    metalTip2.setSource("Institute of Scrap Recycling Industries");

                    // Organic recycling tips
                    RecyclingTip organicTip1 = new RecyclingTip();
                    organicTip1.setWasteCategory(savedOrganic);
                    organicTip1.setTitle("Composting in Small Spaces");
                    organicTip1.setDescription("No yard? No problem! Consider vermicomposting (using worms) in a small bin under your sink or on a balcony. Bokashi fermentation systems can also break down food scraps including meat and dairy in an odor-free bucket that fits in a kitchen cabinet.");
                    organicTip1.setSource("Composting Council");

                    RecyclingTip organicTip2 = new RecyclingTip();
                    organicTip2.setWasteCategory(savedOrganic);
                    organicTip2.setTitle("Coffee Grounds: Garden Gold");
                    organicTip2.setDescription("Used coffee grounds make excellent fertilizer for acid-loving plants like roses, azaleas, and blueberries. They also deter slugs and snails naturally. Sprinkle directly around plants or add to your compost pile for a nitrogen boost.");
                    organicTip2.setSource("University Agricultural Extension");

                    // Electronic recycling tips
                    RecyclingTip electronicTip1 = new RecyclingTip();
                    electronicTip1.setWasteCategory(savedElectronic);
                    electronicTip1.setTitle("Precious Metals Recovery");
                    electronicTip1.setDescription("One metric ton of circuit boards contains 40-800 times the concentration of gold found in gold ore, plus valuable copper, aluminum, and rare earth elements. Proper e-waste recycling recovers these valuable materials while preventing toxic components from entering landfills.");
                    electronicTip1.setSource("Electronics Recycling Coordination Clearinghouse");

                    RecyclingTip electronicTip2 = new RecyclingTip();
                    electronicTip2.setWasteCategory(savedElectronic);
                    electronicTip2.setTitle("Data Security in Recycling");
                    electronicTip2.setDescription("Before recycling electronics, protect your personal information by performing a factory reset on phones and tablets. For computers, use data wiping software that overwrites your hard drive multiple times, or physically remove and destroy the drive before recycling the rest of the computer.");
                    electronicTip2.setSource("National Cybersecurity Alliance");

                    // Hazardous waste recycling tips
                    RecyclingTip hazardousTip1 = new RecyclingTip();
                    hazardousTip1.setWasteCategory(savedHazardous);
                    hazardousTip1.setTitle("Battery Recycling Essentials");
                    hazardousTip1.setDescription("Different battery types require different recycling approaches. Alkaline batteries (AA, AAA, etc.) can often go in regular trash in some regions, but rechargeable batteries contain valuable metals and hazardous materials that must be properly recycled. Many electronics stores offer free battery recycling drop-off.");
                    hazardousTip1.setSource("Call2Recycle");

                    RecyclingTip hazardousTip2 = new RecyclingTip();
                    hazardousTip2.setWasteCategory(savedHazardous);
                    hazardousTip2.setTitle("Paint Disposal Alternatives");
                    hazardousTip2.setDescription("Before disposing of paint, consider donating usable leftovers to community programs, schools, or theater groups. For unusable latex paint, mix with cat litter or paint hardener until solid, then it can often go in regular trash. Oil-based paints always require hazardous waste disposal.");
                    hazardousTip2.setSource("PaintCare");

                    recyclingTipRepository.saveAll(Arrays.asList(
                        plasticTip1, plasticTip2, paperTip1, paperTip2, glassTip1, glassTip2,
                        metalTip1, metalTip2, organicTip1, organicTip2, electronicTip1, electronicTip2,
                        hazardousTip1, hazardousTip2
                    ));
                    System.out.println("Recycling tips initialized");
                }
            }
            } catch (MongoException | DataAccessException e) {
                System.err.println("MongoDB connection error: " + e.getMessage());
                System.err.println("The application will continue to run without database initialization.");
                System.err.println("Please check your MongoDB connection settings.");
            }
        };
    }
}
