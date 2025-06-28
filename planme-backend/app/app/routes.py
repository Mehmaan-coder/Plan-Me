from fastapi import APIRouter
from .models import MoodLog
from .database import db

router = APIRouter()

# ✅ POST endpoint — add mood log
@router.post("/add-mood/")
async def add_mood(log: MoodLog):
    db["users"].update_one(
        {"_id": log.user_id},
        {"$set": {f"moodLogs.{log.date}": {"mood": log.mood}}},
        upsert=True
    )
    return {"message": "Mood log added successfully."}

# ✅ GET endpoint — retrieve mood logs for given user_id, sorted by date
@router.get("/get-moods/{user_id}")
async def get_moods(user_id: str):
    user = db["users"].find_one({"_id": user_id}, {"_id": 0, "moodLogs": 1})

    if not user or "moodLogs" not in user:
        return []

    mood_logs = [
        {"date": date, "mood": data["mood"]}
        for date, data in user["moodLogs"].items()
    ]

    return sorted(mood_logs, key=lambda x: x["date"])
