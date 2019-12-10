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
    type VARCHAR(40) NOT NULL,
    name VARCHAR(40) NOT NULL,
    updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

create table countries (
    id bigint default nextval('data_id_seq'),
    type VARCHAR(40) NOT NULL,
    name VARCHAR(40) NOT NULL,
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

INSERT INTO countries (type, name) VALUES ('country', 'France');
INSERT INTO countries (type, name) VALUES ('country', 'Poland');
INSERT INTO countries (type, name) VALUES ('country', 'United Kingdom');
INSERT INTO countries (type, name) VALUES ('country', 'Italy');
INSERT INTO countries (type, name) VALUES ('country', 'Portugal');
INSERT INTO countries (type, name) VALUES ('country', 'Spain');
INSERT INTO countries (type, name) VALUES ('country', 'Switzerland');
INSERT INTO countries (type, name) VALUES ('country', 'Netherlands');
INSERT INTO countries (type, name) VALUES ('country', 'Ireland');
INSERT INTO countries (type, name) VALUES ('country', 'Luxembourg');
INSERT INTO countries (type, name) VALUES ('country', 'Deutschland');
INSERT INTO items (type, name) VALUES ('item', 'ChainSaw');
INSERT INTO items (type, name) VALUES ('item', 'Car');
INSERT INTO items (type, name) VALUES ('item', 'Microwave');
INSERT INTO items (type, name) VALUES ('item', 'CellPhone');
INSERT INTO items (type, name) VALUES ('item', 'Cow');
INSERT INTO items (type, name) VALUES ('item', 'Beef');
INSERT INTO items (type, name) VALUES ('item', 'Brick');
INSERT INTO items (type, name) VALUES ('item', 'Wood');
INSERT INTO items (type, name) VALUES ('item', 'GoldenToilet');
INSERT INTO items (type, name) VALUES ('item', 'Castle');
INSERT INTO items (type, name) VALUES ('item', 'Hamburger');


