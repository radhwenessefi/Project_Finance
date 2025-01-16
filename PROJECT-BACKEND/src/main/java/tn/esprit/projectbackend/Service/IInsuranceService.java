package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Insurance;
import tn.esprit.projectbackend.Entity.InsuranceType;
import tn.esprit.projectbackend.Entity.Refund;

import java.util.List;

public interface IInsuranceService {

    public Insurance addInsurance(Insurance insurance);

    public void deleteInsurance(int Id_Study) ;

    public Insurance updateInsurance(Insurance insurance) ;

    public List<Insurance> getAllInsurance() ;

    public Insurance getInsurance(int Id_Study) ;
    public List<Insurance> searchByType(InsuranceType type) ;
    public String purchaseInsurance(long accountId, long insuranceId, long packId) ;
    public String refundAccountOnLoss(long accountId, double lossAmount) ;
    public List<Refund> getRefundHistory(long accountId) ;
    public void calculateVarianceAndProcessRefunds() ;






    }
