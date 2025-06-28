from pydantic import BaseModel

class MoodLog(BaseModel):
    user_id: str
    date: str       # Format: YYYY-MM-DD
    mood: str       # e.g. "happy", "sad", "angry", etc.
