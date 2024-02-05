package com.ssafy.lam.hospital.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Category {
    @Id
    @GeneratedValue
    @Column(name="category_seq")
    private Long categorySeq;

    @Column(name="part")
    private String part;
}
