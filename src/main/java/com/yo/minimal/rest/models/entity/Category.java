package com.yo.minimal.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name="name_category")
    private String nameCategory;

    private String description;

//    @OneToMany (fetch = FetchType.LAZY)
//    @JoinColumn (name = "class")
//    private SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CClass aCClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public SubCategory getSubCategory() {
//        return subCategory;
//    }
//
//    public void setSubCategory(SubCategory subCategory) {
//        this.subCategory = subCategory;
//    }
//
////
    public CClass getaCClass() {
        return aCClass;
    }

    public void setaCClass(CClass aCClass) {
        this.aCClass = aCClass;
    }

    private static final long serialVersionUID = 1L;
}
