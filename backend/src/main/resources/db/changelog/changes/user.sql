--liquibase formatted sql

--changeset 07232022-01 author:ehutson
--preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS "user" (
    user_id BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY(user_id),
    UNIQUE(username)
);
--rollback DROP TABLE "user"