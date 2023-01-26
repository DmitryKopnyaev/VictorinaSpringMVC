package com.dimentor.victorina.repository;

import com.dimentor.victorina.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value="SELECT * FROM task INNER JOIN test_task tt on task.id = tt.task_id inner join test t on tt.test_id = t.id WHERE t.id =:id", nativeQuery = true)
    List<Task> getTasksByTestId(@Param("id") long id);

    Optional<Task> getTaskByQuestionAndCorrectAnswer(String question, String correctAnswer);
}
