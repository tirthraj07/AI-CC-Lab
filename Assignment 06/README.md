### What is an Expert System?

An **Expert System** is a computer program designed to simulate the decision-making ability of a human expert. It uses knowledge and logical reasoning to solve complex problems in a specific domain, like medical diagnosis or troubleshooting.

---

#### 1. **Knowledge Base (KB)**

* The **knowledge base** stores facts and rules about the problem domain.
* It contains:

  * **Facts** – things that are currently known to be true (e.g., the user has a fever).
  * **Rules** – logical statements (IF-THEN) that relate facts to conclusions.

**Example:**

```text
IF fever AND cough THEN possible flu
```

---

#### 2. **Inference Engine**

* The **inference engine** is the brain of the expert system.
* It applies logical rules to the facts in the knowledge base to deduce new facts or reach conclusions.
* It uses strategies like:

  * **Forward Chaining** – starts with known facts and applies rules to infer new facts.
  * **Backward Chaining** – starts with a goal and works backward to see if the known facts support it.

---

#### 3. **User Interface**

* Allows users to interact with the system by entering symptoms or answering questions.
* The system uses this input to update facts and provide conclusions or recommendations.

---

### What are Rules?

* Rules are **IF-THEN** statements used by the inference engine to derive conclusions.
* They are based on expert knowledge and represent decision logic.

---

### What are Facts?

* Facts are known or user-provided data.
* Example: “User has a sore throat” is a fact that can be used by rules to infer a diagnosis.

----

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
---

1. **Rule: `{fever}` ⇒ `infection`**    
   Fires → Adds `infection`  
   ➤ `"Monitor Fever. Maybe early signs of infection"`  
   **Updated Rules {`fatigue`, `headache`, `fever`, `cough`, `infection`}**  
  
2. **Rule: `{fatigue, headache}` ⇒ `dehydration`**  
   Fires → Adds `dehydration`  
   ➤ `"Fatigue and headache together often indicate dehydration"`   
   **Updated Rules {`fatigue`, `headache`, `fever`, `cough`, `infection`, `dehydration`}**   

4. **Rule: `{dehydration, fever}` ⇒ `heat_exhaustion`**
   Fires because `dehydration` was just added + `fever` already present
   ➤ `"Fever and dehydration together may suggest overheating or heat exhaustion"`
   **Updated Rules {`fatigue`, `headache`, `fever`, `cough`, `infection`, `dehydration`, `heat_exhaustion`}**
