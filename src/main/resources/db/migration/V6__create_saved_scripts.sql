CREATE SEQUENCE saved_scripts_id_seq;

CREATE TABLE saved_scripts (
                               id          BIGINT PRIMARY KEY DEFAULT nextval('saved_scripts_id_seq'),
                               user_id     BIGINT NOT NULL,
                               script_id   BIGINT NOT NULL,
                               created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               created_by  VARCHAR(100),
                               modified_at TIMESTAMP,
                               modified_by VARCHAR(100),
                               deleted     BOOLEAN NOT NULL DEFAULT FALSE,

                               CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                               CONSTRAINT fk_script FOREIGN KEY (script_id) REFERENCES scripts (id) ON DELETE CASCADE
);

ALTER SEQUENCE saved_scripts_id_seq OWNED BY saved_scripts.id;