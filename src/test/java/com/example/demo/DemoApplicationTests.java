package com.example.demo;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}


	@Autowired
	private UserMapper userMapper;

	@Test
	//@Transactional
	public void test(){

		User user = new User();
		user.setPassword("123456");
		user.setPhone("13125182663");
		//user.setUserId(1);
		user.setUserName("LICSLAN");
		userMapper.insert(user);
		User u = userMapper.selectByPrimaryKey(1000);
		Assert.assertEquals("LICSLAN", u.getUserName());
	}
}
