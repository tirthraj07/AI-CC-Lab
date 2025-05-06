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
