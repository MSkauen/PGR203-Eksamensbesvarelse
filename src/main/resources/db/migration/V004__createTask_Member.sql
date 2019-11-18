CREATE TABLE TASK_MEMBERS
(
    id        SERIAL PRIMARY KEY,
    task_id   INTEGER NOT NULL REFERENCES tasks (id),
    member_id INTEGER NOT NULL REFERENCES members (id),
    UNIQUE(task_id, member_id)
);