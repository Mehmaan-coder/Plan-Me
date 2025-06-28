from pydantic import BaseModel
from typing import List, Optional

class PlanRequest(BaseModel):
    goals: List[str]
    free_time_blocks: List[str]  # ISO‑8601 datetime strings
    mood: Optional[str] = "neutral"

class Task(BaseModel):
    title: str
    start_time: Optional[str]
    end_time: Optional[str]

class PlanResponse(BaseModel):
    plan: List[Task]
