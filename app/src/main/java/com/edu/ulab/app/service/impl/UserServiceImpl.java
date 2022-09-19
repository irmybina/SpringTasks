package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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

        List<UserEntity> userEntities = Storage.getUsers();
        userEntities.add(userMapper.userDtoToUserEntity(userDto));
        Storage.setUsers(userEntities);
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id

        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Storage.getUsers()
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getId() == userDto.getId())
                .peek(user -> user.setFullName(userDto.getFullName()))
                .peek(user -> user.setTitle(userDto.getTitle()))
                .peek(user -> user.setAge(userDto.getAge()));
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = Storage.getUsers()
                .stream()
                .filter(Objects::nonNull)
                .filter(u -> u.getId() == id)
                .findAny().orElse(null);
        return userMapper.userEntityToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        List<UserEntity> users = Storage.getUsers().stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getId() != id)
                .toList();
        Storage.setUsers(users);

    }
}
