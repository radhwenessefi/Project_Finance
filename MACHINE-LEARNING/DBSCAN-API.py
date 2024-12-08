from fastapi import FastAPI, HTTPException
from typing import Optional
import pickle
import pandas as pd
import json
import logging
import os
from dbscan_module import DbscanClustering

app = FastAPI()

# Configure logging
logging.basicConfig(level=logging.DEBUG)

# Test route
@app.get("/")
async def scoring_endpoint():
    return {"message": "Hello, world!"}

# Load the DBSCAN model from pickle file
model_path = "C:/Users/DELL/Desktop/Project_Finance/MACHINE-LEARNING/dbscan_model.pkl"
if not os.path.exists(model_path):
    raise FileNotFoundError(f"Pickle file not found at {model_path}")

with open(model_path, "rb") as model_file:
    try:
        loaded_dbscan_instance = pickle.load(model_file)
        logging.debug(f"Pickle file loaded successfully: {type(loaded_dbscan_instance)}")
    except Exception as e:
        logging.error(f"Error loading pickle file: {e}")
        raise e

# Endpoint to apply DBSCAN
@app.get("/apply_dbscan")
async def apply_dbscan(
    eps_value: Optional[float] = 0.3,
    min_samples_value: Optional[int] = 4,
    csv_filename: Optional[str] = "C:/Users/DELL/Desktop/Project_Finance/MACHINE-LEARNING/top_40_stocks_average.csv",
    k: Optional[int] = 3,
):
    try:
        logging.debug("Received request to /apply_dbscan with parameters:")
        logging.debug(f"eps_value={eps_value}, min_samples_value={min_samples_value}, csv_filename={csv_filename}, k={k}")

        # Validate CSV file path
        if not os.path.exists(csv_filename):
            raise FileNotFoundError(f"CSV file not found at {csv_filename}")

        # Update loaded DBSCAN instance with new parameters
        loaded_dbscan_instance.eps = eps_value
        loaded_dbscan_instance.min_samples = min_samples_value
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