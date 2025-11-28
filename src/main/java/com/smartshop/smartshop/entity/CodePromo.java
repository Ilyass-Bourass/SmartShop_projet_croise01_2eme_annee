package com.smartshop.smartshop.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodePromo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(unique = true,nullable = false)
    private String codePromo;
    private Boolean isActif;
    private LocalDateTime dateCreation;


    @PrePersist
    public void prePersist()
    {
        isActif=true;
        dateCreation=LocalDateTime.now();
    }

}
