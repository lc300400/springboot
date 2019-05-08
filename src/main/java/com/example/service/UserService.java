package com.example.service;

import com.example.model.User;
import java.util.List;

public interface UserService {

    int addUser(User user);
    List<User> findAllUser(int pageNum, int pageSize);

    User getById(Integer id);

    int deleteByPrimaryKey(Integer userId);

}
