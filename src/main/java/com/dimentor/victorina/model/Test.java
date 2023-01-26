package com.dimentor.victorina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test")
@Repository
@Data
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @JsonProperty(value = "response_code")
    private int responseCode;

    @ManyToMany
    @JoinTable(name = "test_task",
            joinColumns = {@JoinColumn(name = "test_id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id")})
    @ToString.Exclude
    @JsonProperty(value = "results")
    private List<Task> tasks = new ArrayList<>();

    @JsonIgnore
    private boolean finished = false;

    public boolean allTaskChecked() {
        return this.tasks.stream().allMatch(o -> o.getResults().size() > 0);
    }
}
