CREATE SEQUENCE liked_scripts_id_seq;

CREATE TABLE liked_scripts (
                               id          BIGINT PRIMARY KEY DEFAULT nextval('liked_scripts_id_seq'),
                               user_id     BIGINT NOT NULL,
                               script_id   BIGINT NOT NULL,
                               created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               created_by  VARCHAR(100),
                               modified_at TIMESTAMP,
                               modified_by VARCHAR(100),

                               CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                               CONSTRAINT fk_script FOREIGN KEY (script_id) REFERENCES scripts (id) ON DELETE CASCADE,

                               CONSTRAINT unique_user_script UNIQUE (user_id, script_id)
);

ALTER SEQUENCE liked_scripts_id_seq OWNED BY liked_scripts.id;
