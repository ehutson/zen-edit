--liquibase formatted sql

--changeset  ehutson:07232022-01
--preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS users
(
    user_id  BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(50) UNIQUE  NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(60)         NOT NULL,
    PRIMARY KEY (user_id)
);
--rollback DROP TABLE users

