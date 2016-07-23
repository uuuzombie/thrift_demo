package com.sky.demo.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.sky.demo.thrift.common.User;
import com.sky.demo.thrift.common.UserType;
import com.sky.demo.thrift.service.DemoService;
import org.apache.thrift.TException;

import java.util.List;

/**
 * Created by rg on 2016/7/23.
 */
public class DemoServiceImpl implements DemoService.Iface {

    private static final List<User> users = Lists.newArrayList();

    static {
        for (int i = 0;i < UserType.values().length; ++i) {
            User user = new User();
            user.setName(String.valueOf(i));
            user.setUserType(UserType.findByValue(i + 1));
            user.setAge((short) (i + 10));
            user.setSex(i % 2 == 0 ? true : false);
            user.setSalary(1000.0);

            users.add(user);
        }
    }



    @Override
    public List<User> getUserList(UserType userType) throws TException {
        Preconditions.checkNotNull(userType, "UserType is null");
        List<User> userList = Lists.newArrayList();
        for (User user : users) {
            if (userType.equals(user.getUserType())) {
                userList.add(user);
            }
        }

        return userList;
    }
}
