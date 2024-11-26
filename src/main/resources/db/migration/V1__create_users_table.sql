CREATE TABLE users
(
    id          BIGINT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by  VARCHAR(100),
    modified_at TIMESTAMP,
    modified_by VARCHAR(100),
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE
);