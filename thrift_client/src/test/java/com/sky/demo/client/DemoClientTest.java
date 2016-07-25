package com.sky.demo.client;

import com.sky.demo.thrift.common.User;
import com.sky.demo.thrift.common.UserType;
import org.junit.Test;

import java.util.List;

/**
 * Created by rg on 2016/7/25.
 */
public class DemoClientTest {

    @Test
    public void test_getUserList() {
        DemoClient client = new DemoClient();
        List<User> userList = client.getUserList(UserType.ADMIN);
        System.out.println(userList);
    }


}
