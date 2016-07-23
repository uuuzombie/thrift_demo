package com.sky.demo.server;

import com.sky.demo.service.DemoServiceImpl;
import com.sky.demo.thrift.service.DemoService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rg on 2016/7/23.
 */
public class DemoServer {

    private static final Logger logger = LoggerFactory.getLogger(DemoServer.class);

    public static final int SERVER_PORT = 9000;
    public static final int TIMEOUT = 10000;    // ms
    public static final String DEMO_PROTOCOL_NAME = "demo_service";

    public void startServer() {
        logger.info("demo service start...");

        try {
            //======Transport====
            //1.TServerSocket 简单的单线程服务模型，一般用于测试
            TServerTransport tServerTransport = new TServerSocket(SERVER_PORT);

            //2.TSSLTransportFactory
//            TSSLTransportFactory.TSSLTransportParameters parameters = new TSSLTransportFactory.TSSLTransportParameters();
//            parameters.setKeyStore("", "");     // key
//            parameters.setTrustStore("", "");   // crt
//
//            TServerTransport tServerTransport = TSSLTransportFactory.getServerSocket(SERVER_PORT, TIMEOUT, null, parameters);


            //======Processor====
            //1.TProcessor
            //TProcessor tProcessor = new DemoService.Processor<DemoService.Iface>(new DemoServiceImpl());

            //2.TMultiplexedProcessor 可注册多个 processor
            TMultiplexedProcessor tProcessor = new TMultiplexedProcessor();
            tProcessor.registerProcessor(DEMO_PROTOCOL_NAME, new DemoService.Processor(new DemoServiceImpl()));

            //======Server====
            TServer.Args tArgs = new TServer.Args(tServerTransport);
            tArgs.processor(tProcessor);
            // set transport
            //tArgs.transportFactory(new TFramedTransport.Factory());

            //======Protocol====
            //1.Binary 默认
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            //2.Compact
            //tArgs.protocolFactory(new TCompactProtocol.Factory());
            //3.Json
            //tArgs.protocolFactory(new TJSONProtocol.Factory());


            TServer server = new TSimpleServer(tArgs);
            server.serve();


        } catch (TTransportException e) {
            logger.error("demo server start error!", e);
        }
    }


    public static void main(String[] args) {
        DemoServer demoServer = new DemoServer();
        demoServer.startServer();

    }
}
