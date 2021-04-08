package com.codecta.academy.services.model;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class ParkDto {
    private Integer id;
    @NotBlank(message = "Location must be set.")
    private String location;
    private String attractions;
    private List<CharacterDto> disneyCharacters = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAttractions() {
        return attractions;
    }

    public void setAttractions(String attractions) {
        this.attractions = attractions;
    }

    public List<CharacterDto> getDisneyCharacters() {
        return disneyCharacters;
    }

    public void setDisneyCharacters(List<CharacterDto> disneyCharacters) {
        this.disneyCharacters = disneyCharacters;
    }
}
