package com.dimentor.victorina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne
    private Category category;

    @NonNull
    private String type;

    @NonNull
    private String difficulty;

    @NonNull
    @Column(nullable = false, unique = true)
    private String question;

    @NonNull
    @Column(nullable = false, name = "correct_answer")
    @JsonProperty(value = "correct_answer")
    private String correctAnswer;

    @ElementCollection
    @JsonProperty(value = "incorrect_answers")
    private List<String> incorrectAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    @ToString.Exclude
    private List<Result> results = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "test_task",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "test_id")})
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Test> tasks = new ArrayList<>();

    //перемешанные ответы
    @JsonIgnore
    public List<String> getMixAnswers() {
        int addIndex = (int) (Math.random() * incorrectAnswers.size());
        List<String> list = new ArrayList<>(this.incorrectAnswers);
        list.add(addIndex, correctAnswer);
        return list;
    }
}

//WRITE - видно когда создаем из json
//READ - видно когда пишем в json
