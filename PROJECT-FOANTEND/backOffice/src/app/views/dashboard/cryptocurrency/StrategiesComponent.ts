import { Component, OnInit } from '@angular/core';
import { StrategiesService } from 'app/shared/services/Event/StrategiesService';

@Component({
  selector: 'app-strategies',
  templateUrl: './strategies.component.html',
  styleUrls: ['./strategies.component.scss']
})
export class StrategiesComponent implements OnInit {
  // Entrées utilisateur
  investmentAmount: number = 15000; // Montant initial
  dcaInterval: number = 7; // Intervalle DCA (jours)
  dipThreshold: number = 0.1; // Seuil pour Buy the Dip

  // Résultats des stratégies
  dcaResult: number = 0;
  buyTheDipResult: number = 0;
  hodlResult: number = 0;

  prices: number[] = []; // Données historiques des prix (simulées)

  constructor(private strategiesService: StrategiesService) {}

  ngOnInit(): void {
    // Simuler des données historiques pour le test
    this.prices = Array.from({ length: 30 }, (_, i) => 50000 + Math.sin(i) * 2000 + Math.random() * 1000);
  }

 
}
