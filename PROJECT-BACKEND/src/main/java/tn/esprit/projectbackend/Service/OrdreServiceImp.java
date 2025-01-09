package tn.esprit.projectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.Ordre;
import tn.esprit.projectbackend.Repository.OrdreRepository;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrdreServiceImp implements IOrdreService {
    @Autowired
    private OrdreRepository ordreRepository;


    public void save(Ordre ordre) {
        // Assurez-vous de remplir le timestamp à la création de l'ordre
        ordre.setTimestamp(LocalDateTime.now());


        ordreRepository.save(ordre);
    }
    public Ordre modifyOrder(Ordre ordre) {
        return ordreRepository.save(ordre);


    }
    public List<Ordre> retrieveAllOrders() {

        List<Ordre> listB = ordreRepository.findAll();


        return listB;
    }
    public void removeOrder(Long id) {
        ordreRepository.deleteById(id);
    }

    public Ordre retrieveOrder(Long id) {
        return ordreRepository.findById(id).get();
    }

}
