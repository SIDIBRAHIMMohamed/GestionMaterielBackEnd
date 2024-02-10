package com.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.entities.Materiel;
import com.project.entities.Reservation;
import com.project.entities.Utilisateur;
import com.project.repositories.MaterielRepository;
import com.project.repositories.ReservationRepository;
import com.project.repositories.UtilisateurRepository;

@Component
public class UserAndMaterialCommandLineRunner implements CommandLineRunner{
    

        private final UtilisateurRepository userRepository;
    

        private final MaterielRepository materialRepository;

        private final ReservationRepository reservationRepository;

        public UserAndMaterialCommandLineRunner(UtilisateurRepository userRepository, MaterielRepository materialRepository, ReservationRepository reservationRepository) {
            this.userRepository = userRepository;
            this.materialRepository = materialRepository;
            this.reservationRepository = reservationRepository;
        }
    
        @Override
        public void run(String... args) throws Exception {

            if(userRepository.count() > 0 || materialRepository.count() > 0) {
                return;
            }

            Utilisateur admin = new Utilisateur("admin", "admin", "admin.admin@univ-tours.fr", "admin-password", 0);
            userRepository.save(admin);
            // create three users with role 0 give them names
            Utilisateur user1 = new Utilisateur("John", "Jack","jack.john@entreprise.fr","John123", 0);
            userRepository.save(user1);

            Utilisateur user2 = new Utilisateur("Lionel", "Smith", "lionel.smith@entreprise.fr", "Lionel123", 0);
            userRepository.save(user2);

            Utilisateur user3 = new Utilisateur("Jack", "Johnson", "jack.johnson@entreprise.fr", "Jack123", 0);
            userRepository.save(user3);

            Utilisateur user4 = new Utilisateur("Soffie", "Williams", "soffie.williams@entreprise.fr", "Soffie123", 0);
            userRepository.save(user4);
    
            List<Materiel> materials = new ArrayList<>();

            // Add different tech items
            materials.add(new Materiel("Laptop", "Windows 10", "LAP-01", 0));
            materials.add(new Materiel("Laptop", "MacOS", "LAP-02", 0));
            materials.add(new Materiel("PC", "Windows 10", "PC-01", 0));
            materials.add(new Materiel("PC", "Linux", "PC-02", 0));
            materials.add(new Materiel("Phone", "Android", "PHN-01", 0));
            materials.add(new Materiel("Phone", "iOS", "PHN-02", 0));
            materials.add(new Materiel("Tablet", "Android", "TAB-01", 0));
            materials.add(new Materiel("Tablet", "iOS", "TAB-02", 0));


            materials.add(new Materiel("Monitor", "4K", "MON-01", 0));
            materials.add(new Materiel("Monitor", "1080p", "MON-02", 0));
            materials.add(new Materiel("Keyboard", "Mechanical", "KEY-01", 0));
            materials.add(new Materiel("Keyboard", "Membrane", "KEY-02", 0));
            materials.add(new Materiel("Mouse", "Wireless", "MOU-01", 0));
            materials.add(new Materiel("Mouse", "Wired", "MOU-02", 0));
            materials.add(new Materiel("Printer", "Laser", "PRI-01", 0));
            materials.add(new Materiel("Printer", "Inkjet", "PRI-02", 0));
            materials.add(new Materiel("Scanner", "Flatbed", "SCA-01", 0));
            materials.add(new Materiel("Scanner", "Sheet-fed", "SCA-02", 0));
            materials.add(new Materiel("Webcam", "1080p", "WEB-01", 0));
            materials.add(new Materiel("Webcam", "720p", "WEB-02", 0));
            materials.add(new Materiel("Speaker", "Stereo", "SPE-01", 0));
            materials.add(new Materiel("Speaker", "Mono", "SPE-02", 0));
            materials.add(new Materiel("Router", "Wireless", "ROU-01", 0));
            materials.add(new Materiel("Router", "Wired", "ROU-02", 0));
            materials.add(new Materiel("Modem", "DSL", "MOD-01", 0));
            materials.add(new Materiel("Modem", "Cable", "MOD-02", 0));
            materials.add(new Materiel("Projector", "4K", "PRO-01", 0));
            materials.add(new Materiel("Projector", "1080p", "PRO-02", 0));

            // Save all materials to the repository
            for (Materiel material : materials) {
                materialRepository.save(material);
            }

            // Reservation reservation = new Reservation(new Date(), new Date(), user, material);
        }
    
}
