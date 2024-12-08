import pandas as pd
from sklearn.cluster import DBSCAN
from sklearn.metrics import silhouette_score
from sklearn.neighbors import NearestNeighbors
import numpy as np
import matplotlib.pyplot as plt
import pickle
import os
from fastapi import HTTPException

class DbscanClustering:
    
    def __init__(self, esp_value, min_samples_value, csv_filename, k):
        self.esp_value = esp_value
        self.min_samples_value = min_samples_value
        self.csv_filename = csv_filename
        self.k = k
        self.features = None
        self.df = None
        self.clustering = None

    def get_data(self):
        self.df = pd.read_csv(self.csv_filename)
        self.features = self.df[["Open", "High", "Low", "Close", "Adj Close", "Volume"]].values

    def nearest_neighbors(self):
        nn_model = NearestNeighbors(n_neighbors=self.k)
        nn_model.fit(self.features)
        distances, indices = nn_model.kneighbors(self.features)
        distances = np.sort(distances, axis=0)
        distances = distances[:, 1]  # Use the distance to the k-th nearest neighbor
  

        # Set the eps value to the average of the distances
        average_value = np.mean(distances)
        self.esp_value = average_value  # Adjust the eps value dynamically
        print(f"Calculated eps value: {self.esp_value}")

    def apply_dbscan(self):
        self.nearest_neighbors()  # Update the eps value from nearest neighbors
        dbscan = DBSCAN(eps=self.esp_value, min_samples=self.min_samples_value)
        self.clustering = dbscan.fit(self.features)
        print("The eps value used for DBSCAN:", self.esp_value)  # Corrected to use self.esp_value

    def accuracy_dbscan(self):
        cluster_labels = self.clustering.labels_
        
        # Check if only one unique label is assigned (i.e., all points are noise)
        unique_labels = set(cluster_labels)
        if len(unique_labels) <= 1:
            raise HTTPException(status_code=400, detail="No meaningful clusters found. Try adjusting eps or min_samples.")

        self.df['Cluster_Labels'] = cluster_labels
        silhouette_avg = silhouette_score(self.features, cluster_labels)
        print("Silhouette Score:", silhouette_avg)
        print(self.df.columns)
        self.df.to_csv("output.csv", index=False)
        print("Data saved to output.csv")
        return self.df



    def save_model(self, filename="dbscan_model.pkl"):
        current_directory = os.getcwd()
        full_path = os.path.join(current_directory, filename)
        with open(full_path, 'wb') as file:
            pickle.dump(self, file)
        print(f"Model saved to {full_path}")


# Adjusted parameter values for experimentation
esp_value = 0  # Initial value, will be updated by nearest_neighbors()
min_samples_value = 4  # Adjust as needed
csv_filename = "C:/Users/DELL/Desktop/Project_Finance/MACHINE-LEARNING/top_40_stocks_average.csv"
k = 10  # Adjust to capture broader neighborhood

# Instantiate and execute DBSCAN clustering
dbscan_instance = DbscanClustering(esp_value, min_samples_value, csv_filename, k)
dbscan_instance.get_data()
dbscan_instance.apply_dbscan()  # Automatically calls nearest_neighbors to update eps

# Check for meaningful clusters and plot results
try:
    dbscan_instance.accuracy_dbscan()
    dbscan_instance.save_model()
except HTTPException as e:
    print(f"Error: {e.detail}")
