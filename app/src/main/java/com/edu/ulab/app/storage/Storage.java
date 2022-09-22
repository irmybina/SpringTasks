package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    private static Map<Long, UserEntity> users = new HashMap<>();
    private static Map<Long, BookEntity> books = new HashMap<>();
    public static void setUsers(Map<Long, UserEntity> users) {
        Storage.users = users;
    }

    public static void setBooks(Map<Long, BookEntity> books) {
        Storage.books = books;
    }

    public static Map<Long, UserEntity> getUsers(){
        return users;
    }

    public static Map<Long, BookEntity> getBooks(){
        return books;
    }

    public static void addUser(Long id, UserEntity userEntity){
        users.put(id, userEntity);
    }
    public static void removeUser(Long id){
        users.remove(id);
    }

    public static void addBook(Long id, BookEntity bookEntity){
        books.put(id, bookEntity);
    }

    public static void removeBook(Long id){
        books.remove(id);
    }
    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у юзера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции
}
