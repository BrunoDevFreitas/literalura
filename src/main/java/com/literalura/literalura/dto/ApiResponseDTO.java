package com.literalura.literalura.dto;

import java.util.List;

public class ApiResponseDTO {

    private int count;
    private String next;
    private String previous;
    private List<BookDTO> results;

    // Constructors
    public ApiResponseDTO() {}

    public ApiResponseDTO(int count, String next, String previous, List<BookDTO> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    // Getters and Setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<BookDTO> getResults() {
        return results;
    }

    public void setResults(List<BookDTO> results) {
        this.results = results;
    }
}
