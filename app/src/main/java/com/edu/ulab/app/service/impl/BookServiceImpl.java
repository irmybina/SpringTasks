package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        log.info("Set book id: {}", bookId);


        Storage.addBook(bookDto.getId(), bookMapper.bookDtoToBookEntity(bookDto));
        log.info("Book added to Storage: {}", bookDto);

        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if (Storage.getBooks().containsKey(bookDto.getId())){
            Storage.removeBook(bookDto.getId());
            log.info("last book version deleted: {}", bookDto.getId());
            Storage.addBook(bookDto.getId(), bookMapper.bookDtoToBookEntity(bookDto));
            log.info("New book added: {}", bookDto.getId());
        }
        else {
            throw new NotFoundException("book was not found");
        }

        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        if (Storage.getBooks().containsKey(id)){
            BookDto bookDto = bookMapper.bookEntityToBookDto(Storage.getBooks().get(id));
            bookDto.setId(id);
            log.info("found book: {}", bookDto);
            return bookDto;
        }

        else {
            throw new NotFoundException("book was not found");
        }
    }

    @Override
    public List<Long> getBooksByUserId(Long userId){

        List<Long> books = Storage.getBooks()
                .entrySet()
                .stream()
                .filter(book -> book.getValue().getUserId()==userId)
                .map(Map.Entry::getKey)
                .toList();
        log.info("Collected book ids: {}", books);
        if (!books.isEmpty()){
        return books;
        }
        else {
            throw new NotFoundException("user was not found");
        }

    }

    @Override
    public void deleteBookById(Long id) {
        if (Storage.getBooks().containsKey(id)) {
            Storage.removeBook(id);
            log.info("Book deleted: {}", id);
        }
        else {
            throw new NotFoundException("user was not found");
        }
    }
}
