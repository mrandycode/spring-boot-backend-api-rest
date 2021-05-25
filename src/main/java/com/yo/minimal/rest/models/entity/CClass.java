package com.yo.minimal.rest.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "class")
public class CClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "name_class")
    private String nameClass;

    private String description;
//
//    @OneToMany (fetch = FetchType.LAZY)
//    private List<Category> category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<Category> getCategory() {
//        return category;
//    }
//
//    public void setCategory(List<Category> category) {
//        this.category = category;
//    }

    private static final long serialVersionUID = 1L;
}