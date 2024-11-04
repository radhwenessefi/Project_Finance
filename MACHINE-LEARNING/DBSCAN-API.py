# sart to deploy the dbscan model

from fastapi import FastAPI,  HTTPException, Body
import pickle
from typing import Optional 
import pandas as pd
from sklearn.cluster import DBSCAN
import numpy as np
import json
from dbscan_module import DbscanClustering

app = FastAPI()
@app.get('/')
async def scoring_endpoint():
    return {"hello":"word"}

with open("C:/Users/DELL/Desktop/PIDEV-PROJECT-PIONEER/PIDEV-Machine-Learning/dbscan_model.pkl", "rb") as model_file:

    loaded_dbscan_instance = pickle.load(model_file)

# Define the endpoint for receiving POST requests
@app.get("/apply_dbscan")
async def apply_dbscan(
    
    esp_value: Optional[float] = 5,
    min_samples_value: Optional[int] = 5,
    csv_filename: Optional[str] = "C:/Users/DELL/Desktop/Project_Finance/MACHINE-LEARNING/top_40_stocks_data.csv",
    k: Optional[int] = 3,
):
    try:
        # Set the parameters in the loaded instance
        loaded_dbscan_instance.esp_value = esp_value
        loaded_dbscan_instance.min_samples_value = min_samples_value
        loaded_dbscan_instance.csv_filename = csv_filename
        loaded_dbscan_instance.k = k

        # Call the apply_dbscan method
        loaded_dbscan_instance.apply_dbscan()

        # Get the clustering labels
        cluster_labels = loaded_dbscan_instance.clustering.labels_.tolist()
        result_df = loaded_dbscan_instance.accuracy_dbscan()
        result_json = result_df.to_json(orient='records')
        result_object = json.loads(result_json)
        # Return the result as a JSON response
        return result_object

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))