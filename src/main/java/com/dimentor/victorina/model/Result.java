package com.dimentor.victorina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "result", uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "task_id"})})
@Data
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Task task;

    @Column(nullable = false)
    @NonNull
    private String answer;
}

//WRITE - видно когда создаем из json
//READ - видно когда пишем в json