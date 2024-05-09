package com.alura.literalura.main;

import com.alura.literalura.model.BookInfo;
import com.alura.literalura.model.Data;
import com.alura.literalura.services.DataConvert;
import com.alura.literalura.services.RequestAPI;

import java.util.Optional;
import java.util.Scanner;

public class MainTasks {
    
    private Scanner scanner = new Scanner(System.in);
    private RequestAPI requestAPI = new RequestAPI();
    private DataConvert dataConvert = new DataConvert();
    private final String BASE_URL = "https://gutendex.com/books/";
    
    public Optional<BookInfo> getBookData (){
        System.out.println("Introduzca el nombre del libro que desea buscar");
        var bookTitle = scanner.nextLine();
        var json = requestAPI.getData(BASE_URL + "?search=%20" + bookTitle.replace(" ","+"));
        var bookInfo = dataConvert.getData(json, Data.class);
        
        Optional<BookInfo> searchedBook = bookInfo.results().stream()
                .filter(l -> l.title().toUpperCase().contains(bookTitle.toUpperCase()))
                .findFirst();
        
        if ( searchedBook.isPresent() ) {
          searchedBook.get();
          searchedBook.stream()
                  .forEach(System.out::println);
        }
        return searchedBook;
    }
}
