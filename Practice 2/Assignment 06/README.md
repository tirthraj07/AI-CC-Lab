```
Do you have FATIGUE? (y/n) : y
Do you have COLD? (y/n) : n
Do you have HEADACHE? (y/n) : y
Do you have COUGH? (y/n) : y
Do you have FEVER? (y/n) : y
Do you have BODY ACHES? (y/n) : n
Do you have FLU? (y/n) : n
Do you have SNEEZING? (y/n) : n
Do you have DEHYDRATION? (y/n) : n
Do you have RUNNY NOSE? (y/n) : n
=== Possible Conclusions ===
Monitor Fever. Maybe early signs of infection
Fever and dehydration together may suggest overheating or heat exhaustion.
Fatigue and headache together often indicate dehydration.
```

---

* `fatigue`, `headache`, `fever`, `cough`
* `cold`, `body_aches`, `flu`, `runny_nose`, etc.

---

1. **Rule: `{fever}` ⇒ `infection`**
   Fires → Adds `infection`
   ➤ `"Monitor Fever. Maybe early signs of infection"`

2. **Rule: `{fatigue, headache}` ⇒ `dehydration`**
   Fires → Adds `dehydration`
   ➤ `"Fatigue and headache together often indicate dehydration"`

3. **Rule: `{dehydration, fever}` ⇒ `heat_exhaustion`**
   Fires because `dehydration` was just added + `fever` already present
   ➤ `"Fever and dehydration together may suggest overheating or heat exhaustion"`
