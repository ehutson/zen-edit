CREATE TABLE IF NOT EXISTS document (
  document_id bigint GENERATED ALWAYS AS IDENTITY,
  user_id BIGINT,
  name VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  PRIMARY KEY(document_id),
  CONSTRAINT fk_user
    FOREIGN KEY(user_id)
    REFERENCES "user"(user_id)
);