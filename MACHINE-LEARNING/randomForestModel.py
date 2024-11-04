import pandas as pd
import numpy as np
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.metrics import r2_score, mean_squared_error, mean_absolute_percentage_error
from keras.models import Sequential, load_model
from keras.layers import LSTM,Dense
from tensorflow import keras
import pickle
import os
import dill
import matplotlib.pyplot as plt
from sklearn.ensemble import RandomForestRegressor, AdaBoostRegressor
from sklearn.preprocessing import MinMaxScaler

class PredictVolumes:
    def __init__(self, csv_filename, simple_test):
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
        X = self.df[["Open", "High", "Low", "Close", "Adj Close"]]
        y = self.df[["Volume"]]
     

    def Correlation(self):
        corr_value = self.df.corr()
        tril_index = np.tril_indices_from(corr_value)
        for coord in zip(*tril_index):
            corr_value.iloc[coord[0],coord[1]]= np.NAN
        corr_value = (corr_value
                   .stack()
                   .to_frame()
                   .reset_index()
                   .rename(columns={'level_0':'feature1',
                                    'level_1':'feature2',
                                    0:'correlation'}))
        corr_value['abs_correlation'] = corr_value.correlation.abs()
        return corr_value

    def CorrelationGraph(self):
        matrice_correlation = self.df.corr()
        plt.figure(figsize=(10, 8))
        sns.heatmap(matrice_correlation, annot=True, cmap='coolwarm', fmt=".2f", linewidths=.5)
        plt.title('Correlation Matrix')
        plt.show()

    def dropCorrolateColumns(self):
        # Exclude non-numeric columns
        numeric_df = self.df.select_dtypes(include=np.number)
        
        correlation_matrix = numeric_df.corr()
        columns_to_drop = set()
        threshold = 0.99
        
        for i in range(len(correlation_matrix.columns)):
            for j in range(i+1, len(correlation_matrix.columns)):
                if abs(correlation_matrix.iloc[i, j]) >= threshold:
                    columns_to_drop.add(correlation_matrix.columns[j])
        
        df = self.df.drop(columns_to_drop, axis=1)
        return df


    def Encoding(self):
        if "Symbol" in self.df.columns and "Date" in self.df.columns:
            df = self.df.drop(["Symbol", "Date"], axis=1)
        else:
            df = self.df
        Cat_columns = df.select_dtypes(include=['object']).columns.tolist()
        for c in Cat_columns:
            df[c] = df[c].map({"sell": 0, "buy": 1, "none": 2})
        return df

    
    def spliting_and_scaling(self, test_size=0.2):
        # Drop NaN values
        self.df = self.df.dropna().reset_index(drop=True)
        print(self.df)
        # Encode categorical variables
        self.df=self.Encoding()

        # Exclude non-numeric columns
        #X = self.df.drop(["Volume"], axis=1)
        X = self.df[["Open", "High", "Low", "Close", "Adj Close"]]
        y = self.df["Volume"]

        # Create and fit scaler (avoid unnecessary re-fitting)
        scaler = MinMaxScaler()
        X_scaled = scaler.fit_transform(X)

        return train_test_split(X_scaled, y, test_size=test_size, random_state=42)



    def trainRandomForestModel(self):

        X_train, X_test, y_train, y_test = self.spliting_and_scaling()
        rf_model = RandomForestRegressor()
        rf_model.fit(X_train, y_train)
        rf_pred = rf_model.predict(X_test)
        return rf_pred
    
    def trainRandomForestModel(self):
        X_train, X_test, y_train, y_test = self.spliting_and_scaling()
        rf_model = RandomForestRegressor()
        rf_model.fit(X_train, y_train)
        self.model = rf_model  # Assign the trained model to the attribute
        rf_pred = rf_model.predict(X_test)
        return rf_pred
    
    def evaluation(self, y_true, y_pred):
        r2 = r2_score(y_true, y_pred)
        rmse = mean_squared_error(y_true, y_pred, squared=False)
        mape = mean_absolute_percentage_error(y_true, y_pred)

        print("R²: ", r2)
        print("RMSE: ", rmse)
        print("MAPE: ", mape)
    def predict_real_values(self):
        # Reshape the simple test data to have two dimensions
        simple_test_2d = self.simple_test.reshape(1, -1)
        # Make predictions
        predictions = self.model.predict(simple_test_2d)
        return predictions
    def save_model(self, filename="random_model.pkl"):
        current_directory = os.getcwd()
        full_path = os.path.join(current_directory, filename)
        with open(full_path, 'wb') as file:
            pickle.dump(self.model, file)
        print(f"Model saved to {full_path}")
    

# Example usage:
simple_test = np.array([130.279998779296, 130.899993896484, 124.169998168945, 124.21630859375, 124.21630859375])
predictor = PredictVolumes("C:\\Users\\DELL\\Desktop\\PI_infini\\data_with_indicateur.csv", simple_test)
predictor.importData()
#predictor.CorrelationGraph()
df_after_dropping = predictor.dropCorrolateColumns()
#encoded_df = predictor.Encoding()
X_train, X_test, y_train, y_test = predictor.spliting_and_scaling()
y_hat = predictor.trainRandomForestModel()
pre = predictor.predict_real_values()
print("your value is ",pre)
# Perform evaluation
predictor.evaluation(y_test, y_hat)
predictor.save_model()
