DELETE FROM task_incorrect_answers WHERE task_incorrect_answers.task_id > 0;
DELETE FROM test_task WHERE task_id > 0;
DELETE FROM result where id > 0;
DELETE FROM task where id > 0;
DELETE FROM test where id > 0;