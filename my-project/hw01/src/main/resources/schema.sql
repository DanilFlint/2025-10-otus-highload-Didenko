CREATE TABLE IF NOT EXISTS USERS
(
    id   BIGSERIAL PRIMARY KEY,
    username varchar(255),
    lastname varchar(255),
    date_of_birth varchar(255),
    city varchar(255),
    gender varchar(255),
    interests varchar(255),
    password varchar(255)
);

CREATE TABLE IF NOT EXISTS POSTS
(
    id   BIGSERIAL PRIMARY KEY,
    author_user_id BIGINT,
    text TEXT,
    create_date TIMESTAMPTZ DEFAULT NOW(),
    CONSTRAINT fk_posts_author
        FOREIGN KEY (author_user_id)
        REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS FRIENDS (
    user_id VARCHAR(255) NOT NULL,
    friend_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    CONSTRAINT check_self_friending CHECK (user_id <> friend_id)
);
