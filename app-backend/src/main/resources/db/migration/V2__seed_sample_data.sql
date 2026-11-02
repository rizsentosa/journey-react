-- Active: 1793378627586@@127.0.0.1@5439@app_db@public
DO $$
DECLARE
    i INT;
    v_todo_id UUID;
    v_due_date DATE;
    v_titles TEXT[] := ARRAY['Work', 'Personal', 'Shopping', 'Health', 'Finance', 'Project', 'Learning', 'Home', 'Travel', 'Fitness'];
    v_actions TEXT[] := ARRAY['Review', 'Prepare', 'Draft', 'Finalize', 'Schedule', 'Update', 'Clean', 'Organize', 'Buy', 'Send'];
    v_objects TEXT[] := ARRAY['report', 'meeting notes', 'groceries', 'budget', 'presentation', 'codebase', 'documentation', 'emails', 'workout plan', 'flight tickets'];
BEGIN
    FOR i IN 1..100 LOOP
        v_todo_id := gen_random_uuid();
        v_due_date := CURRENT_DATE + (i || ' days')::INTERVAL;
        
        -- Insert into todo_management
        INSERT INTO todo_management (id, title, description, due_date)
        VALUES (
            v_todo_id,
            v_titles[(i % 10) + 1] || ' Category ' || i,
            'Detailed description for todo item ' || i || ' regarding upcoming tasks.',
            v_due_date
        );
        
        -- Insert corresponding record into task_todo referencing the todo_management id
        INSERT INTO task_todo (id, todo_id, title)
        VALUES (
            gen_random_uuid(),
            v_todo_id,
            v_actions[(i % 10) + 1] || ' ' || v_objects[((i + 3) % 10) + 1] || ' #' || i
        );
    END LOOP;
END $$;