package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String detail;
}
