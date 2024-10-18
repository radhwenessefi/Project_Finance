package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Actions;

public interface IActionsService {

    Actions getRealTimeAction(String symbol);
}
