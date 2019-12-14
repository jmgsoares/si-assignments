Create SCHEMA "Results";

create sequence data_id_seq;

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

create table items (
    id bigint default nextval('data_id_seq'),
    type VARCHAR(40) NOT NULL default 'item',
    name VARCHAR(40) NOT NULL UNIQUE,
    updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

create table countries (
    id bigint default nextval('data_id_seq'),
    type VARCHAR(40) NOT NULL default 'country',
    name VARCHAR(40) NOT NULL UNIQUE,
    updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

CREATE TRIGGER set_timestamp_items
BEFORE UPDATE ON items
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp_countries
BEFORE UPDATE ON countries
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

INSERT INTO countries (name) VALUES ('France');
INSERT INTO countries (name) VALUES ('Poland');
INSERT INTO countries (name) VALUES ('United Kingdom');
INSERT INTO countries (name) VALUES ('Italy');
INSERT INTO countries (name) VALUES ('Portugal');
INSERT INTO countries (name) VALUES ('Spain');
INSERT INTO countries (name) VALUES ('Switzerland');
INSERT INTO countries (name) VALUES ('Netherlands');
INSERT INTO countries (name) VALUES ('Ireland');
INSERT INTO countries (name) VALUES ('Luxembourg');
INSERT INTO countries (name) VALUES ('Deutschland');
INSERT INTO items (name) VALUES ('ChainSaw');
INSERT INTO items (name) VALUES ('Car');
INSERT INTO items (name) VALUES ('Microwave');
INSERT INTO items (name) VALUES ('CellPhone');
INSERT INTO items (name) VALUES ('Cow');
INSERT INTO items (name) VALUES ('Beef');
INSERT INTO items (name) VALUES ('Brick');
INSERT INTO items (name) VALUES ('Wood');
INSERT INTO items (name) VALUES ('GoldenToilet');
INSERT INTO items (name) VALUES ('Castle');
INSERT INTO items (name) VALUES ('Hamburger');


