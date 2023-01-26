package com.dimentor.victorina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @NonNull
    @Column(nullable = false, unique = true)
    @JsonValue
    @JsonProperty("name")
    private String value;

    @OneToMany(mappedBy = "category")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore //*
    @ToString.Exclude
    private List<Task> tasks = new ArrayList<>();
}
