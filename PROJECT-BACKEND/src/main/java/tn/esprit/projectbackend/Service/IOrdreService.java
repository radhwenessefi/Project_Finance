package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Ordre;

import java.util.List;

public interface IOrdreService {
    public Ordre modifyOrder(Ordre ordre);
    public List<Ordre> retrieveAllOrders();
    void save(Ordre ordre);
    public void removeOrder(Long id);
    public Ordre retrieveOrder(Long id);
}
