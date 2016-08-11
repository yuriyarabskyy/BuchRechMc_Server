## Posting questions

Everything relatively to **/questions**.

HTTP Post method is used

**/pushQuestions**

Questions are passed in the body, which is a json document. If you want to
create a new question don't mention the id, it will be automatically
generated. If you want to change a question under an existing id, include the id
in the question definition.

Example (new version)

```json
[
  {
    "content": "2+2=?",
	  "rightAnswerIndex": 0,
	  "topic": "Weatheorology",
	  "chapter": 1,
	  "hint": "No clues here",
    "correctAnswerId": 2,
    "isBookingEntry": false
  },
  {
    "id": 1,
    "content": "2+2=?",
	  "rightAnswerIndex": 0,
	  "topic": "Weatheorology",
	  "chapter": 1,
	  "hint": "No clues here",
    "correctAnswerId": 2,
    "isBookingEntry": false
  }
]
```

The first element creates a new question in the database and the second one alters the existing question under the id '1'.

## Getting questions

1. **/getAll**

HTTP Get

ResponseBody is a list of all the questions.

2. **/getById**

Http Post

Body is a comma separated list of ids of the questions you want to get. For
example: 1,2,3,5,7

3. **/getByContent**

Http Post

Write the content of the question in the body.

## Creating users

Relatively to **/users**.

1. **/create** - post method

```json
{
  "lastName"="Winchester",
  "firstName"="Sam"
}
```

2. **/getUsersByName?lastName=Winchester&firstName=Sam**

Both parameters are not required. For instance you may look for a person only based on the lastName or only on the firstName.
