CREATE TABLE TASK_MEMBERS
(
    id        SERIAL PRIMARY KEY,
    task_id   INTEGER NOT NULL REFERENCES projects (id),
    member_id INTEGER NOT NULL REFERENCES members (id)
);