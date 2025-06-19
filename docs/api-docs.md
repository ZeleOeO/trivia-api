## 📚 Endpoints

### `POST` `/quiz/submit`
**Operation:** `submitQuiz`  
**Tags:** question-controller

#### 🧾 Parameters
_None_
#### 📦 Request Body
**Content Type:** `application/json`
- Schema: [AnswerRequest](#answerrequest)
#### 📤 Responses
| Code | Description | Schema |
|------|-------------|--------|
| 200 | OK | [DefaultAPIResponseAnswerResponse](#defaultapiresponseanswerresponse) |

---
### `GET` `/quiz/{id}`
**Operation:** `getQuiz`  
**Tags:** question-controller

#### 🧾 Parameters
| Name | In | Type | Required |
|------|----|------|----------|
| id | path | `string` | True |

#### 📦 Request Body
_None_
#### 📤 Responses
| Code | Description | Schema |
|------|-------------|--------|
| 200 | OK | [DefaultAPIResponseQuiz](#defaultapiresponsequiz) |

---
### `GET` `/quiz/start`
**Operation:** `getQuestions`  
**Tags:** question-controller

#### 🧾 Parameters
| Name | In | Type | Required |
|------|----|------|----------|
| num | query | `integer` | True |
| cat | query | `integer` | True |

#### 📦 Request Body
_None_
#### 📤 Responses
| Code | Description | Schema |
|------|-------------|--------|
| 200 | OK | [DefaultAPIResponseListQuestion](#defaultapiresponselistquestion) |

---

## 📦 Schemas

### Answer
<a name="answer"></a>

| Field | Type |
|-------|------|
| question_id | `string` |
| correct | `boolean` |
| correct_answer | `string` |
| selected_option | `string` |

### AnswerRequest
<a name="answerrequest"></a>

| Field | Type |
|-------|------|
| quiz_id | `string` |
| answers | `array` |

### AnswerResponse
<a name="answerresponse"></a>

| Field | Type |
|-------|------|
| quiz_id | `string` |
| score | `integer` |
| total_questions | `integer` |
| answers | `array` |

### DefaultAPIResponseAnswerResponse
<a name="defaultapiresponseanswerresponse"></a>

| Field | Type |
|-------|------|
| status | `integer` |
| message | `string` |
| data | `object` |

### DefaultAPIResponseQuiz
<a name="defaultapiresponsequiz"></a>

| Field | Type |
|-------|------|
| status | `integer` |
| message | `string` |
| data | `object` |

### Question
<a name="question"></a>

| Field | Type |
|-------|------|
| questionText | `string` |
| options | `object` |
| category | `string` |
| type | `string` |
| question_id | `string` |

### Quiz
<a name="quiz"></a>

| Field | Type |
|-------|------|
| id | `string` |
| questions | `array` |

### DefaultAPIResponseListQuestion
<a name="defaultapiresponselistquestion"></a>

| Field | Type |
|-------|------|
| status | `integer` |
| message | `string` |
| data | `array` |
