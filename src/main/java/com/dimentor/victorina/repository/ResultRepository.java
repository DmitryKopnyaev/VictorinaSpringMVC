package com.dimentor.victorina.repository;

import com.dimentor.victorina.model.Client;
import com.dimentor.victorina.model.Result;
import com.dimentor.victorina.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> getByClientAndTask(Client client, Task task);
}
