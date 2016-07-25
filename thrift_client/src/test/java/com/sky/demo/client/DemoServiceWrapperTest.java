package com.sky.demo.client;

import com.sky.demo.model.ThriftServerInfo;
import com.sky.demo.thrift.common.User;
import com.sky.demo.thrift.common.UserType;
import org.junit.Test;

import java.util.List;

/**
 * Created by rg on 2016/7/25.
 */
public class DemoServiceWrapperTest {

    @Test
    public void test_getUserList(){
        DemoServiceWrapper wrapper = new DemoServiceWrapper();
        ThriftServerInfo info = new ThriftServerInfo();
        info.setIp("127.0.0.1");
        info.setPort(9000);
        info.setServerTag("demo_service");

        List<User> userList = wrapper.getUserList(info, UserType.ADMIN);
        System.out.println(userList);
    }
}
