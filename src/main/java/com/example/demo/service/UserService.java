package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
/**   *
 * @author licslan
 * */
public interface UserService {
    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
