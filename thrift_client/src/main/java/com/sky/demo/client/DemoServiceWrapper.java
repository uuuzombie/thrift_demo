package com.sky.demo.client;

import com.sky.demo.model.ThriftServerInfo;
import com.sky.demo.thrift.common.User;
import com.sky.demo.thrift.common.UserType;
import com.sky.demo.thrift.service.DemoService;
import com.sky.demo.util.ThriftUtils;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by rg on 2016/7/23.
 */
public class DemoServiceWrapper {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceWrapper.class);

    public List<User> getUserList(ThriftServerInfo info, UserType userType) {
        List<User> userList = null;
        try {
            logger.info("thrift server info:{}", info);

            TMultiplexedProtocol protocol = ThriftUtils.buildProtocol(info);
            DemoService.Client client = new DemoService.Client(protocol);

            userList = client.getUserList(userType);

        } catch (Exception e) {
            logger.error("get user error", e);
        } finally {
            ThriftUtils.close();
        }

        return userList;
    }

}
