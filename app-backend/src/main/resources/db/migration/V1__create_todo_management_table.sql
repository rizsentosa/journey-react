-- Active: 1793378627586@@127.0.0.1@5439@app_db@public
CREATE TABLE todo_management (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    title VARCHAR(150) NOT NULL,
    description TEXT,
    due_date DATE
)

CREATE TABLE task_todo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    todo_id UUID NOT NULL,
    title VARCHAR(150) NOT NULL,
    CONSTRAINT fk_task_todo FOREIGN KEY (todo_id) REFERENCES todo_management (id)
)