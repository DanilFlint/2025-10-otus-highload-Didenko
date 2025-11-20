CREATE TABLE IF NOT EXISTS USERS
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(255),
    lastname varchar(255),
    dateOfBirth varchar(255),
    city varchar(255),
    gender varchar(255),
    interests varchar(255),
    password varchar(255)

);
