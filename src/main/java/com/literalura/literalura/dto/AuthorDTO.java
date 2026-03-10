package com.literalura.literalura.dto;

public class AuthorDTO {

    private String name;
    private int birth_year;
    private int death_year;

    // Constructors
    public AuthorDTO() {}

    public AuthorDTO(String name, int birth_year, int death_year) {
        this.name = name;
        this.birth_year = birth_year;
        this.death_year = death_year;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year;
    }
}
