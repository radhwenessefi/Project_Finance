import { Injectable } from '@angular/core';
//import * as tf from '@tensorflow/tfjs';

@Injectable({
  providedIn: 'root',
})
export class SentimentAIService {
  private model: any;

  constructor() {}

 /* async loadModel(): Promise<void> {
    this.model = await tf.loadLayersModel('/assets/sentiment-model/model.json');
    console.log('Modèle chargé avec succès.');
  }

  analyzeSentiment(text: string): number {
    if (!this.model) {
      console.error('Modèle non chargé.');
      return 0;
    }

    const inputTensor = this.preprocessText(text);
    const prediction = this.model.predict(inputTensor) as tf.Tensor;
    const sentimentScore = prediction.dataSync()[0];
    return sentimentScore;
  }

  private preprocessText(text: string): tf.Tensor {
    const maxLength = 100; // Longueur maximale des séquences
    const words = text.toLowerCase().split(' ');
    const encodedWords = words.map((word) => this.wordToIndex(word));
    const paddedSequence = this.padSequence(encodedWords, maxLength);
    return tf.tensor2d([paddedSequence]);
  }

  private wordToIndex(word: string): number {
    // Exemple d'indexation des mots (remplacez par votre vocabulaire)
    const vocabulary = { bitcoin: 1, great: 2, bad: 3, future: 4 };
    return vocabulary[word] || 0; // Retourne 0 si le mot est inconnu
  }

  private padSequence(sequence: number[], maxLength: number): number[] {
    const padded = new Array(maxLength).fill(0);
    for (let i = 0; i < Math.min(sequence.length, maxLength); i++) {
      padded[i] = sequence[i];
    }
    return padded;
  }*/
}
