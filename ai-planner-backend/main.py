from fastapi import FastAPI, HTTPException
from models import PlanRequest, PlanResponse
from planner import generate_plan

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "AI Planner is running"}

@app.post("/generate_plan", response_model=PlanResponse)
async def create_plan(request: PlanRequest):
    try:
        return generate_plan(request)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
