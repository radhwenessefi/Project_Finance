from fastapi import FastAPI, HTTPException
from typing import Optional 
import json
from pydantic import BaseModel
from keras.models import load_model
import numpy as np
import pickle


app = FastAPI()

class VolumesItem(BaseModel):
    Open: float
    High: float  # Corrected typo: Hight -> High
    Low: float
    Close: float
    Adj_close: float

@app.get('/')
async def scoring_endpoint():
    return {"hello": "world"}  # Corrected "word" -> "world"

@app.post('/Prediction')
async def prediction_volume(item: VolumesItem):
    try:
        # Extract values directly from the item
        values_list = [item.Open, item.High, item.Low, item.Close, item.Adj_close]
        array_list = np.array(values_list).reshape(1, -1)
        
        # Load the model
        with open("C:/Users/DELL/Desktop/PIDEV-PROJECT-PIONEER/PIDEV-Machine-Learning/random_model.pkl", "rb") as model_file:
            loaded_prediction_instance = pickle.load(model_file)
        
        # Make predictions
        predicted_value = loaded_prediction_instance.predict(array_list)
        
        # Convert NumPy array to Python list
        predicted_value_list = predicted_value.tolist()  

        # Debugging: print predicted_value_list
        print("Predicted value list:", predicted_value_list)

        # Convert the list to JSON
        predicted_json = json.dumps(predicted_value_list)
        return {predicted_json}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

