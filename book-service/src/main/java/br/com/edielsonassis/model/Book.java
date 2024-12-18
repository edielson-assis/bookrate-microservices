package br.com.edielsonassis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
public class Book implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(name = "launch_date", nullable = false)
    private LocalDate launchDate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, length = 180)
    private String title;

    @Column(nullable = false, length = 255)
    private String description;
}