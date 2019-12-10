CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

create table info(
    id   SERIAL,
    type VARCHAR(40) NOT NULL,
    name VARCHAR(40) NOT NULL,
    updated TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON info
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

INSERT INTO info (type, name) VALUES ('country', 'France');
INSERT INTO info (type, name) VALUES ('country', 'Poland');
INSERT INTO info (type, name) VALUES ('country', 'United Kingdom');
INSERT INTO info (type, name) VALUES ('country', 'Italy');
INSERT INTO info (type, name) VALUES ('country', 'Portugal');
INSERT INTO info (type, name) VALUES ('country', 'Spain');
INSERT INTO info (type, name) VALUES ('country', 'Switzerland');
INSERT INTO info (type, name) VALUES ('country', 'Netherlands');
INSERT INTO info (type, name) VALUES ('country', 'Ireland');
INSERT INTO info (type, name) VALUES ('country', 'Luxembourg');
INSERT INTO info (type, name) VALUES ('country', 'Deutschland');
INSERT INTO info (type, name) VALUES ('item', 'ChainSaw');
INSERT INTO info (type, name) VALUES ('item', 'Car');
INSERT INTO info (type, name) VALUES ('item', 'Microwave');
INSERT INTO info (type, name) VALUES ('item', 'CellPhone');
INSERT INTO info (type, name) VALUES ('item', 'Cow');
INSERT INTO info (type, name) VALUES ('item', 'Beef');
INSERT INTO info (type, name) VALUES ('item', 'Brick');
INSERT INTO info (type, name) VALUES ('item', 'Wood');
INSERT INTO info (type, name) VALUES ('item', 'GoldenToilet');
INSERT INTO info (type, name) VALUES ('item', 'Castle');
INSERT INTO info (type, name) VALUES ('item', 'Hamburger');


