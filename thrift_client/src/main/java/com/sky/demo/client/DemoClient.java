package com.sky.demo.client;

import com.sky.demo.thrift.common.User;
import com.sky.demo.thrift.common.UserType;
import com.sky.demo.thrift.service.DemoService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by rg on 2016/7/23.
 */
public class DemoClient {

    private static final Logger logger = LoggerFactory.getLogger(DemoClient.class);

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 9000;
    public static final int TIMEOUT = 10000;    //ms

    public static final String DEMO_PROTOCOL_NAME = "demo_service";

    public List<User> getUserList(UserType userType) {
        List<User> userList = null;
        TTransport tTransport = null;
        try {
            //======Transport====
            //1.TServerSocket 简单的单线程服务模型，一般用于测试
            tTransport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            //tTransport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
            tTransport.open();

            //2.TSSLTransportFactory
//            TSSLTransportFactory.TSSLTransportParameters parameters = new TSSLTransportFactory.TSSLTransportParameters();
//            parameters.setKeyStore("", "");
//            parameters.setTrustStore("", "");
//            parameters.requireClientAuth(true);
//
//            tTransport = TSSLTransportFactory.getClientSocket(SERVER_IP, SERVER_PORT, TIMEOUT, parameters);
//            tTransport = new TFramedTransport(sslTransport);

            //======Protocol====
            //1.Binary 默认
            TProtocol tProtocol = new TBinaryProtocol(tTransport);
            //2.Compact
            //TProtocol tProtocol = new TCompactProtocol(tTransport);
            //3.Json
            //TProtocol tProtocol = new TJSONProtocol(tTransport);

            //======Client====
            //1.TProtocol
            //DemoService.Client client = new DemoService.Client(tProtocol);

            //2.TMultiplexedProtocol
            TMultiplexedProtocol tMultiplexedProtocol = new TMultiplexedProtocol(tProtocol, DEMO_PROTOCOL_NAME);
            DemoService.Client client = new DemoService.Client(tMultiplexedProtocol);

            userList = client.getUserList(userType);

        } catch (Exception e) {
            logger.error("start client error", e);
        }

        return userList;

    }

    public static void main(String[] args) {
        DemoClient client = new DemoClient();
        List<User> userList = client.getUserList(UserType.ADMIN);
        System.out.println(userList);
    }


}
