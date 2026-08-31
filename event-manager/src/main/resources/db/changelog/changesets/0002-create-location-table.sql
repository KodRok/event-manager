--liquibase formatted sql
--changeset student:V001_create_locations

CREATE TABLE locations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500) NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity >= 5),
    description TEXT
);

--rollback DROP TABLE locations;

--changeset student:V002_insert_test_location
INSERT INTO locations (name, address, capacity, description)
VALUES ('Камчатка', 'ул.Блохина, 15', 30, 'Доброе утро, последний герой!');
