## Server interface

### Methods

1. update database string version

* updateDB
	+ isUpToDate(String version);

Return a json file with a code. If code = true, then everything is fine, else we
have to parse the json file. 

// TODO Create some kind of version cobntrol, which tells you what differences
there are. Somehow take a snapshot of each version and then send all the
differences. 

buchrechmc.com/api/updatedb?version=versionString

2. get the whole database

buchrechmc.com/api/getdb?version=string

buchrechmc.com/api/getlastdb

3. authentification with access tokens

Read a spring project with access tokens.

4. buchrechmc.com/api/statistics/..

5. Adding, changing, deleting a question

http post

buchrechmc.com/api/addquestion

buchrechmc.com/api/updatequestion

buchrechmc.com/api/deletequestion

buchrechmc.com/api/getquestion?id=long

buchrechmc.com/api/getquestions post list of questions

Think about the order of the questions. Id plus number? Or should we change
something with the id?

6. The same goes for the excercises



