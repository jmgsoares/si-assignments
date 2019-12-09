create table countries
(
    id   SERIAL,
    name VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
);


create table items(
    id SERIAL,
    name VARCHAR(40) NOT NULL,
    PRIMARY KEY ( id )
);

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


