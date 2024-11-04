import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from keras.models import Sequential, load_model
from keras.layers import Dense
from tensorflow import keras



class PredictVolumes:
    def __init__(self, csv_filename,simple_test):
        self.csv_filename = csv_filename
        self.df = None
        self.X_train = None
        self.X_test = None
        self.y_train = None
        self.y_test = None
        self.model = None
        self.simple_test = simple_test

    def importData(self):
        self.df = pd.read_csv(self.csv_filename)
        mean_atr = self.df['atr_14'].mean()
        self.df['atr_14'].fillna(mean_atr, inplace=True)
        #print(self.df.isnull().sum())
        X = self.df[["Open", "High", "Low", "Close", "Adj Close"]]
        y = self.df[["Volume"]]
     
        self.X_train, self.X_test, self.y_train, self.y_test = train_test_split(X, y, test_size=0.2)

    def compileModel(self):
        self.model = Sequential()
        self.model.add(Dense(units=32, activation='relu', input_dim=self.X_train.shape[1]))
        self.model.add(Dense(units=64, activation='relu'))
        self.model.add(Dense(units=1, activation='relu'))
        self.model.compile(loss='mean_squared_error', optimizer='sgd', metrics=['mae'])  # MSE loss


    def fitPredictModel(self):
        self.model.fit(self.X_train, self.y_train, epochs=40, batch_size=32)
        y_hat = self.model.predict(self.X_test)
        return y_hat
    def prediction(self):
        simple_test = self.model.predict(self.simple_test)
        return simple_test
        

    def evaluateModel(self, y_hat):
        accuracy = 1 - accuracy_score(self.y_test, y_hat)
        return accuracy
    def saveModel(self):
        
        self.model.save("preModel.h5")  # or "preModel.keras"


# Example usage:
simple_test= np.array([130.279998779296, 130.899993896484, 124.169998168945, 124.21630859375, 124.21630859375])
predictor = PredictVolumes("data_with_indicateur.csv",simple_test)
predictor.importData()
predictor.compileModel()
y_hat = predictor.fitPredictModel()
accuracy = predictor.evaluateModel(y_hat)
predictor.saveModel()
print("Accuracy:", accuracy)