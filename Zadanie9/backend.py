from fastapi import FastAPI, Request, HTTPException
from gpt4all import GPT4All
import json
import re

app = FastAPI()

# im using uvicorn to run the server
# uvicorn backend:app --host 0.0.0.0 --port 8000

try:
    model = GPT4All("Meta-Llama-3-8B-Instruct.Q4_0.gguf", device="gpu")
except Exception as e:
    raise Exception(f"Failed to load model: {str(e)}")

@app.post("/query")
async def query(request: Request):
    try:
        data = await request.json()
        user_text = data['text']

        prompt = user_text
        response = model.generate(
            prompt,
            max_tokens=100,
            temp=0.7,
            top_p=0.9
        )
        generated_text = response.strip()

        generated_text = generated_text[len(prompt):].strip()

        return {"response": generated_text}
    except json.JSONDecodeError:
        raise HTTPException(status_code=400, detail="Invalid JSON format in request body")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal server error: {str(e)}")