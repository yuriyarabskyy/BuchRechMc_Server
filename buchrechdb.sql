create table users (
  id int NOT NULL AUTO_INCREMENT,
  last_name varchar(255) NOT NULL,
  first_name varchar(255),
  login varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  is_admin boolean,
  PRIMARY KEY(id)
);

create table questions (
  id int NOT NULL AUTO_INCREMENT,
  content varchar(1023),
  topic varchar(255),
  chapter int,
  hint varchar(511),
  correct_answer_id int,
  is_booking_entry boolean,
  from_page int NOT NULL,
  to_page int NOT NULL,
  PRIMARY KEY(id)
);

create table answers (
  id int NOT NULL AUTO_INCREMENT,
  question_id int NOT NULL,
  answer_id int NOT NULL,
  answer varchar(511),
  PRIMARY KEY (id),
  FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

create table users_questions (
  id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  question_id int NOT NULL,
  tried boolean,
  correctly_answered boolean,
  given_answer_id int,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

create table lectures (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255),
  start_chapter int,
  end_chapter int,
  PRIMARY KEY(id)
);
