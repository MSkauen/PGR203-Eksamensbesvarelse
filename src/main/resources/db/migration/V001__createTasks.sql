CREATE TYPE STATUS AS ENUM(
    'Not started',
    'In progress',
    'Finished'
);


CREATE TABLE TASKS (
    id SERIAL PRIMARY KEY,
    name VARCHAR(1000) NOT NULL,
    status STATUS NOT NULL DEFAULT 'Not started'
);