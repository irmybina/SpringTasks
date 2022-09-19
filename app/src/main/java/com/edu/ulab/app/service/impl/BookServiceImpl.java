package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private static Long bookId = 0L;
    private BookMapper bookMapper;

    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDto.setId(++bookId);

        List<BookEntity> bookEntities = Storage.getBooks();
        bookEntities.add(bookMapper.bookDtoToBookEntity(bookDto));
        Storage.setBooks(bookEntities);

        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        List<BookEntity> books = Storage.getBooks().stream()
                .filter(Objects::nonNull)
                .filter(book -> book.getId() != bookDto.getId())
                .toList();

        books.add(bookMapper.bookDtoToBookEntity(bookDto));
        Storage.setBooks(books);
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        List<BookEntity> books = Storage.getBooks();
        BookEntity bookEntity = books.stream()
                .filter(Objects::nonNull)
                .filter(book -> book.getId() == id)
                .findAny().orElse(null);

        return bookMapper.bookEntityToBookDto(bookEntity);
    }

    @Override
    public List<Long> getBooksByUserId(Long userId){
        List<Long> books = Storage.getBooks().stream()
                .filter(Objects::nonNull)
                .filter(book -> book.getUserId() == userId)
                .map(book -> book.getId())
                .toList();

        return books;
    }

    @Override
    public void deleteBookById(Long id) {
        List<BookEntity> books = Storage.getBooks();
        List<BookEntity> booksToEdit = books.stream()
                .filter(Objects::nonNull)
                .filter(book -> book.getId() != id)
                .toList();
        Storage.setBooks(booksToEdit);

    }
}
