import os, json
from openai import OpenAI
from dotenv import load_dotenv
from fastapi import HTTPException
from models import PlanRequest, Task, PlanResponse

PLAN_SCHEMA = {
    "type": "object",
    "properties": {
        "plan": {
            "type": "array",
            "items": {
                "type": "object",
                "properties": {
                    "title": {"type": "string", "description": "Task title"},
                    "start_time": {"type": "string", "description": "Start timestamp"},
                    "end_time": {"type": "string", "description": "End timestamp"}
                },
                "required": ["title", "start_time", "end_time"],
                "additionalProperties": False
            }
        }
    },
    "required": ["plan"],
    "additionalProperties": False
}


load_dotenv()
api_key = os.getenv("OPENROUTER_API_KEY")

if not api_key:
    raise ValueError("Missing OPENROUTER_API_KEY in .env")

client = OpenAI(api_key=api_key, base_url="https://openrouter.ai/api/v1")

def generate_plan(request: PlanRequest) -> PlanResponse:
    prompt = """
You are a productivity assistant. Always respond *only* with valid JSON matching this format (no extra text):

{
  "plan": [
    {
      "title": "Task name",
      "start_time": "YYYY-MM-DDThh:mm",
      "end_time": "YYYY-MM-DDThh:mm"
    }
  ]
}

Goals: {request.goals}
Time blocks: {request.free_time_blocks}
Mood: {request.mood}
"""


    resp = client.chat.completions.create(
    model="openai/gpt-4o",
    messages=[{"role": "user", "content": prompt}],
    temperature=0.0,
    max_tokens=300,
    response_format={
        "type": "json_schema",
        "json_schema": {
            "name": "plan_output",
            "strict": True,
            "schema": PLAN_SCHEMA
        }
    }
)

    content = resp.choices[0].message.content
    print("🔹 Structured response:", content)

    data = json.loads(content)
    tasks = [Task(**t) for t in data["plan"]]
    return PlanResponse(plan=tasks)
