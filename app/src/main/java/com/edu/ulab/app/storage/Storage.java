package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static List<UserEntity> users = new ArrayList<>();
    private static List<BookEntity> books = new ArrayList<>();
    public static void setUsers(List<UserEntity> users) {
        Storage.users = users;
    }

    public static void setBooks(List<BookEntity> books) {
        Storage.books = books;
    }

    public static List<UserEntity> getUsers(){
        return users;
    }

    public static List<BookEntity> getBooks(){
        return books;
    }

    public static void addUser(UserEntity userEntity){
        users.add(userEntity);
    }

    public static void addBook(BookEntity bookEntity){
        books.add(bookEntity);
    }

    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у юзера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции
}