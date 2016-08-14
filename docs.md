# Put /api/ before every call

## Questions

Everything relatively to **/questions**.

HTTP Post method is used

1. **/pushQuestions**

Questions are passed in the body, which is a json document. If you want to
create a new question don't mention the id, it will be automatically
generated. If you want to change a question under an existing id, include the id
in the question definition.

All answers in possibleAnswers will be saved automatically into answers.

Example (new version)

```json
[
  {
    "content": "2+2=?",
	  "chapter": 1,
	  "hint": "No clues here",
    "correctAnswerId": 1,
    "isBookingEntry": false,
    "fromPage": 1,
    "toPage": 4,
    "possibleAnswers": [
      {"answerId":1,"answer":"4"},
      {"answerId":1,"answer":"5"},
      {"answerId":1,"answer":"0"},
      {"answerId":1,"answer":"4"},
      {"answerId":1,"answer":"No idea"},
    ]
  },
  {
    "id": 1,
    "content": "2+2=?",
	  "chapter": 1,
	  "hint": "No clues here",
    "correctAnswerId": 2,
    "isBookingEntry": false,
    "fromPage": 1,
    "toPage": 4
  }
]
```

The first element creates a new question in the database and the second one alters the existing question under the id '1'.

2. **/getAll**

HTTP Get

ResponseBody is a list of all the questions.

3. **/getById**

Http Post

Body is a comma separated list of ids of the questions you want to get. For
example: 1,2,3,5,7

4. **/getByContent**

Http Post

Write the content of the question in the body.

5. **/answerQuestion**

```json
{
  "user_id": 1,
  "question_id": 2,
  "answer_id": 3
}
```

## Topics /api/topics

1. **/getAllTopics** - get all topics

2. **/addTopic** - add new topic (POST)

```json
{
  "chapter" : 1,
  "topic" : "Futurism"
}
```

## Users

Relatively to **/api/users**.

1. **/register** - (POST) register new user

An email message with a verification url is sent to the specified email address.

```json
{
  "lastName": "Winchester",
  "firstName": "Sam",
  "password": "secret",
  "email": "your.email@(my)tum.de"
}
```

2. **/getUsersByName?lastName=Winchester&firstName=Sam**

Both parameters are not required. For instance you may look for a person only based on the lastName or only on the firstName.

3. **/getAll** - get all users

## Lectures **/api/lectures**

1. **/getAll** - get all lectures

2. **/getLectureByName?name=Economics** - get a lecture according to it's name

3. **/{filename}** - (GET method) get the lecture

Example: **/Economics.pdf**

4. **Posting new lecture /upload**

  * method="POST" enctype="multipart/form-data"
  * pdf file,  fromChapter, toChapter

5. **/getQuestions?page=123"** - get questions to the selected page
