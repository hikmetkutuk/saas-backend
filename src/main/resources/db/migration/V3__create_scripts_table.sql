CREATE SEQUENCE scripts_id_seq;

CREATE TABLE scripts (
                         id          BIGINT PRIMARY KEY DEFAULT nextval('scripts_id_seq'),
                         title       VARCHAR(200) NOT NULL,
                         description TEXT NOT NULL,
                         content     TEXT NOT NULL,
                         image_url   VARCHAR(1024),
                         is_active   BOOLEAN NOT NULL DEFAULT FALSE,
                         created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         created_by  VARCHAR(100),
                         modified_at TIMESTAMP,
                         modified_by VARCHAR(100),
                         deleted     BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER SEQUENCE scripts_id_seq OWNED BY scripts.id;

CREATE TABLE script_category (
                                 script_id   BIGINT NOT NULL,
                                 category_id BIGINT NOT NULL,
                                 PRIMARY KEY (script_id, category_id),
                                 CONSTRAINT fk_script FOREIGN KEY (script_id) REFERENCES scripts (id) ON DELETE CASCADE,
                                 CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);