package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.ApiResponseForex;

public interface IForexService {
    /**
     * Récupère les données Forex en temps réel pour une paire de devises spécifique,
     * avec les paramètres de plage et de dates.
     *
     * @param pair      La paire de devises (ex. EURUSD)
     * @param range     La plage de données (ex. 1 pour un jour, 5 pour 5 jours)
     * @param dateStart La date de début au format AAAA-MM-JJ
     * @param dateEnd   La date de fin au format AAAA-MM-JJ
     * @return L'objet ApiResponseForex contenant les données Forex
     */
    ApiResponseForex getRealTimeForex(String pair, String range, String dateStart, String dateEnd);
}
