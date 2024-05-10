package com.alura.literalura.main;

import com.alura.literalura.model.Book;
import com.alura.literalura.model.Data;
import com.alura.literalura.repository.IBookRepository;
import com.alura.literalura.services.DataConvert;
import com.alura.literalura.services.RequestAPI;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainTasks {
    
    private Scanner scanner = new Scanner(System.in);
    private RequestAPI requestAPI = new RequestAPI();
    private DataConvert dataConvert = new DataConvert();
    private IBookRepository repository;
    private final String BASE_URL = "https://gutendex.com/books/";
    private List<Book> books;
    private String bookSelected;
    
    public MainTasks ( IBookRepository repository ) {
        this.repository = repository;
    }
    
    public void showMenu () {
        var option = - 1;
        while ( option != 0 ) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();
            
            switch (option) {
                case 1:
                    getBookData();
                    break;
                case 2:
                    showStoredBooks();
                    break;
            }
        }
    }
    
    private String getDataFromUser () {
        System.out.println("Introduzca el nombre del libro que desea buscar");
        bookSelected = scanner.nextLine();
        return bookSelected;
    }
    
    // Función para obtener los datos del libro de la API
    private Data getBookDataFromAPI (String bookTitle ) {
        var json = requestAPI.getData(BASE_URL + "?search=%20" + bookTitle.replace(" ", "+"));
        var data = dataConvert.getData(json, Data.class);
        
        return data;
    }
    
    private Optional<Book> getBookInfo (Data bookData, String bookTitle ) {
        Optional<Book> books = bookData.results().stream()
                .filter(l -> l.title().toLowerCase().contains(bookTitle.toLowerCase()))
                .map(b -> new Book(b.title(), b.languages(), b.downloads(), b.authors()))
                .findFirst();
        return books;
    }
    
    private void buscarSerieWeb () {
        String title = getDataFromUser();
        Data datos = getBookDataFromAPI(title);
        Book book = new Book(datos.results());
        repository.save(book);

        System.out.println(book);
    }
    

    
    // Función principal que utiliza las funciones anteriores
    private Optional<Book> getBookData () {
        String bookTitle = getDataFromUser();
        Data bookInfo = getBookDataFromAPI(bookTitle);
        Optional<Book> book = getBookInfo(bookInfo, bookTitle);
        
        if ( book.isPresent() ) {
            var b = book.get();
            repository.save(b);
            System.out.println(b);
        } else {
            System.out.println("\nLibro no encontrado\n");
        }
        
        return book;
    }
    
    
    private void showStoredBooks () {
        books = repository.findAll();
        
        books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);
    }
}
