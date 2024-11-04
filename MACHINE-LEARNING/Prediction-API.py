from fastapi import FastAPI, HTTPException
from typing import Optional 
import json
from pydantic import BaseModel
from keras.models import load_model
import numpy as np

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
        array_list=np.array(values_list).reshape(1,-1)
        loaded_prediction_instance = load_model("preModel.h5")
        predicted_value = loaded_prediction_instance.predict(array_list)
        predicted_value_list = predicted_value.tolist()  # Convert NumPy array to Python list

# Now you can convert the list to JSON
        predicted_json = json.dumps(predicted_value_list[0][0])
        return{ predicted_json }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
