create table data
(
    id   SERIAL,
    type VARCHAR(40) NOT NULL,
    name VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO data (type, name) VALUES ('country', 'France');
INSERT INTO data (type, name) VALUES ('country', 'Poland');
INSERT INTO data (type, name) VALUES ('country', 'United Kingdom');
INSERT INTO data (type, name) VALUES ('country', 'Italy');
INSERT INTO data (type, name) VALUES ('country', 'Portugal');
INSERT INTO data (type, name) VALUES ('country', 'Spain');
INSERT INTO data (type, name) VALUES ('country', 'Switzerland');
INSERT INTO data (type, name) VALUES ('country', 'Netherlands');
INSERT INTO data (type, name) VALUES ('country', 'Ireland');
INSERT INTO data (type, name) VALUES ('country', 'Luxembourg');
INSERT INTO data (type, name) VALUES ('country', 'Deutschland');
INSERT INTO data (type, name) VALUES ('item', 'ChainSaw');
INSERT INTO data (type, name) VALUES ('item', 'Car');
INSERT INTO data (type, name) VALUES ('item', 'Microwave');
INSERT INTO data (type, name) VALUES ('item', 'CellPhone');
INSERT INTO data (type, name) VALUES ('item', 'Cow');
INSERT INTO data (type, name) VALUES ('item', 'Beef');
INSERT INTO data (type, name) VALUES ('item', 'Brick');
INSERT INTO data (type, name) VALUES ('item', 'Wood');
INSERT INTO data (type, name) VALUES ('item', 'GoldenToilet');
INSERT INTO data (type, name) VALUES ('item', 'Castle');
INSERT INTO data (type, name) VALUES ('item', 'Hamburger');


