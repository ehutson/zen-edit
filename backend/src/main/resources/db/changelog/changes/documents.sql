--liquibase formatted sql

--changeset ehutson:07232022-02
--preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS documents
(
    document_id
    bigint
    GENERATED
    ALWAYS AS
    IDENTITY,
    user_id
    BIGINT,
    title
    VARCHAR
(
    255
) NOT NULL,
    content TEXT NOT NULL,
    PRIMARY KEY
(
    document_id
),
    CONSTRAINT fk_users
    FOREIGN KEY
(
    user_id
)
    REFERENCES users
(
    user_id
)
    );
--rollback DROP TABLE documents