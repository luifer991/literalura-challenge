package com.alura.literalura.model;

import java.util.List;

public class Book {
    private String title;
    private List<Authors> authors;
    private List<String> languages;
    private Double downloads;
    
    public Book ( BookInfo book ) {
        this.title = book.title();
        this.authors = book.authors();
        this.languages = book.languages();
        this.downloads = book.downloads();
    }
    
    public Double getDownloads () {
        return downloads;
    }
    
    public void setDownloads ( Double downloads ) {
        this.downloads = downloads;
    }
    
    public List<String> getLanguages () {
        return languages;
    }
    
    public void setLanguages ( List<String> languages ) {
        this.languages = languages;
    }
    
    public List<Authors> getAuthors () {
        return authors;
    }
    
    public void setAuthors ( List<Authors> authors ) {
        this.authors = authors;
    }
    
    public String getTitle () {
        return title;
    }
    
    public void setTitle ( String title ) {
        this.title = title;
    }
    
    @Override
    public String toString () {
        return
                "Title='" + title + '\'' + "\n" +
                "Authors=" + authors + "\n" +
                "Languages=" + languages + "\n" +
                "Downloads=" + downloads + "\n";
    }
}
