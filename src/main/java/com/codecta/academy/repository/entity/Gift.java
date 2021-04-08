package com.codecta.academy.repository.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(schema = "codecta", name="GIFT")
public class Gift extends ModelObject<Integer> {
    @SequenceGenerator(
            name = "giftSeq",
            sequenceName = "GIFT_SEQ",
            schema = "codecta",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "giftSeq")
    @Id
    private Integer id;
    private String description;
    @ManyToMany(mappedBy = "gifts")
    private List<DisneyCharacter> characters = new ArrayList<>();


    @Override
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DisneyCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(List<DisneyCharacter> characters) {
        this.characters = characters;
    }

}
