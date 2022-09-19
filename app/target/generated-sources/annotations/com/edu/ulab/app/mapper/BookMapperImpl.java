package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.web.request.BookRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-19T19:51:00+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto bookRequestToBookDto(BookRequest bookRequest) {
        if ( bookRequest == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setTitle( bookRequest.getTitle() );
        bookDto.setAuthor( bookRequest.getAuthor() );
        bookDto.setPageCount( bookRequest.getPageCount() );

        return bookDto;
    }

    @Override
    public BookRequest bookDtoToBookRequest(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        BookRequest bookRequest = new BookRequest();

        bookRequest.setTitle( bookDto.getTitle() );
        bookRequest.setAuthor( bookDto.getAuthor() );
        bookRequest.setPageCount( bookDto.getPageCount() );

        return bookRequest;
    }

    @Override
    public BookEntity bookDtoToBookEntity(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId( bookDto.getId() );
        bookEntity.setUserId( bookDto.getUserId() );
        bookEntity.setTitle( bookDto.getTitle() );
        bookEntity.setAuthor( bookDto.getAuthor() );
        bookEntity.setPageCount( bookDto.getPageCount() );

        return bookEntity;
    }

    @Override
    public BookDto bookEntityToBookDto(BookEntity bookEntity) {
        if ( bookEntity == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId( bookEntity.getId() );
        bookDto.setUserId( bookEntity.getUserId() );
        bookDto.setTitle( bookEntity.getTitle() );
        bookDto.setAuthor( bookEntity.getAuthor() );
        bookDto.setPageCount( bookEntity.getPageCount() );

        return bookDto;
    }
}
