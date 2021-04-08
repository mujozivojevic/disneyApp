package com.codecta.academy.services.model;


import java.util.ArrayList;
import java.util.List;

public class GiftDto {
    private Integer id;
    private String description;
    private List<CharacterDto> characters = new ArrayList<>();
    private List<Integer> characterIdList = new ArrayList<>();

    public Integer getId() {
        return id;
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

    public List<CharacterDto> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterDto> characters) {
        this.characters = characters;
    }

    public List<Integer> getCharacterIdList() {
        return characterIdList;
    }

    public void setCharacterIdList(List<Integer> characterIdList) {
        this.characterIdList = characterIdList;
    }
}
