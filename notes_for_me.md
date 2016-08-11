# Database

1. We should define the database manually in sql for stability reasons
2. Users have a manytomany relationship with questions

### Users

| Id             | FirstName      | LastName   | isAdmin |
| :------------- | :------------- | :--------- | :--- |
| 1              | George         | Clooney    | true |

### UserQuestionsAssociation

| UserId         | QuestionId     | Tried      | CorrectlyAnswered | GivenAnswerIndex |
| :------------- | :------------- | :--------- | :-------- | :------ |
| 1              | 1              | true       | false     | 1       |

### Questions

| Id             | Content        | Topic     | Chapter    | Hint    | CorrectAnswerId | isBookingEntry |
| :------------- | :------------- | :-------- | :--------- | :------ | :--------- | :---- |
| 1              | How's the weather today?| Weather | 3 | Look at the sky | 2 | false

### QuestionsPossibleAnswers

| QuestionId     | AnswerId       | Answer |
| :------------- | :------------- | :----- |
| 1              | 1              | Bad    |
| 1              | 2              | Good   |

### Lecture

Pdf's are stored in a separate directory and not in a database. But their id's and names need to to be saved nevertheless.

| Id | Name |
| :------------- | :------------- |
| 1       | Weatherology       |

### QuestionLecture

Connects questions to specific pages of a lecture.

| LectureId      | FromPage       | ToPage   | QuestionsId |
| :------------- | :------------- | :------- | :---------- |
| 1              | 10             | 13       | 1           |

### Excercises

Need to consult you.

### Konto

Need to consult you too.


# Lessons learned

```java
@Transactional
    public VorlesungPdf getPdfByName(String name) {

        String sql = "select * from Vorlesungen where name = :name";

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        query.addEntity(VorlesungPdf.class);

        query.setParameter("name", name);

        List<VorlesungPdf> list = query.list();

        if (!list.isEmpty()) return list.get(0);

        return null;

    }
```