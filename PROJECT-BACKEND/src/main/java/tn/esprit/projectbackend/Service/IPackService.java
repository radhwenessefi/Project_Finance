package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Pack;

import java.util.List;

public interface IPackService {

    public Pack addPack(Pack pack);

    public void deletePack(int Id_Study) ;

    public Pack updatePack(Pack pack) ;

    public List<Pack> getAllPack() ;

    public Pack getPack(int Id_Study) ;

//    public List<Pack> searchByPriceP(long minPrice, long maxPrice) ;
//   public void affecterPackToCompte(Long id_Account, Long IdPack, Float perte) ;

    public double calculateRefund(long accountId, double loss) ;



    }
