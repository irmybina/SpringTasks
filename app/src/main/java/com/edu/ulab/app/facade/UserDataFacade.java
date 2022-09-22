package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .peek(book -> bookService.createBook(book))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book update request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);
        UserDto updatedUser = userService.updateUser(userDto);
        log.info("Updated user: {}", updatedUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .map(bookMapper::bookRequestToBookDto)
                .peek(book -> bookService.updateBook(book))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(updatedUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("Got get user with books by user id request: {}", userId);
        UserDto userDto = userService.getUserById(userId);
        log.info("Got user: {}", userDto);

        List<Long> bookIdList = bookService.getBooksByUserId(userId);
        log.info("Collected book ids: {}", bookIdList);

        if (userDto != null) return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
        else return null;
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Got delete user by id request: {}", userId);

        userService.deleteUserById(userId);
        log.info("user deleted: {}", userId);


        List<Long> books = bookService.getBooksByUserId(userId);
        log.info("Collected book ids: {}", books);

        books.stream()
                .filter(Objects::nonNull)
                .peek(book -> bookService.deleteBookById(book));
        log.info("Deleted books by user id: {}", userId);

    }
}
