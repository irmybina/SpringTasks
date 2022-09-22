package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static Long userId = 0L;
    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(++userId);
        log.info("Set user id: {}", userId);
        Storage.addUser(userDto.getId(), userMapper.userDtoToUserEntity(userDto));
        log.info("User added to Storage: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (Storage.getUsers().containsKey(userDto.getId())){
            Storage.removeUser(userDto.getId());
            log.info("last user version deleted: {}", userDto.getId());
            Storage.addUser(userDto.getId(), userMapper.userDtoToUserEntity(userDto));
            log.info("New user added: {}", userDto);
        }
        else {
            throw new NotFoundException("user was not found");
        }

        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        if (Storage.getUsers().containsKey(id)) {
            UserDto userDto = userMapper.userEntityToUserDto(Storage.getUsers().get(id));
            userDto.setId(id);
            log.info("Found user: {}", userDto);
            return userDto;
        }
        else {
            throw new NotFoundException("user was not found");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        if (Storage.getUsers().containsKey(id)) {
            Storage.removeUser(id);
            log.info("User deleted: {}", id);
        }
        else {
            throw new NotFoundException("user was not found");
        }
    }
}
