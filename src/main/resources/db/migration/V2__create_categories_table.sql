CREATE SEQUENCE categories_id_seq;

CREATE TABLE categories
(
    id          BIGINT PRIMARY KEY DEFAULT nextval('categories_id_seq'),
    name        VARCHAR(100) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by  VARCHAR(100),
    modified_at TIMESTAMP,
    modified_by VARCHAR(100),
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE
);

ALTER SEQUENCE categories_id_seq OWNED BY categories.id;