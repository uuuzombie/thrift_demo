package com.sky.demo.util;

import com.sky.demo.model.ThriftServerInfo;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rg on 2016/7/23.
 */
public class ThriftUtils {

    private static final Logger logger = LoggerFactory.getLogger(ThriftUtils.class);

    public static final int TIMEOUT = 10000;        //ms

    private static ThreadLocal<TTransport> tTransportThreadLocal = new ThreadLocal<>();

    public static TTransport getTTransport() {
        return tTransportThreadLocal.get();
    }

    public static void setTTransport(TTransport tTransport) {
        tTransportThreadLocal.set(tTransport);
    }

    public static void remove() {
        tTransportThreadLocal.remove();
    }

    public static TMultiplexedProtocol buildProtocol(ThriftServerInfo info) throws TTransportException {
        return buildProtocol(info, TIMEOUT);
    }


    public static TMultiplexedProtocol buildProtocol(ThriftServerInfo info, int timeout) throws TTransportException {
        boolean isUseSSL = false;

        TTransport tTransport = null;
        if (isUseSSL) {
            TSSLTransportFactory.TSSLTransportParameters parameters = new TSSLTransportFactory.TSSLTransportParameters();
            parameters.setKeyStore("", "");
            parameters.setTrustStore("", "");
            parameters.requireClientAuth(true);

            tTransport = new TFramedTransport(TSSLTransportFactory.getClientSocket(info.getIp(), info.getPort(), timeout, parameters));
        } else {
            tTransport = new TSocket(info.getIp(), info.getPort(), timeout);
//            tTransport = new TFramedTransport(new TSocket(info.getIp(), info.getPort(), timeout));
            tTransport.open();
        }

        setTTransport(tTransport);

        TProtocol tProtocol = new TBinaryProtocol(tTransport);
        TMultiplexedProtocol tMultiplexedProtocol = new TMultiplexedProtocol(tProtocol, info.getServerTag());
        return tMultiplexedProtocol;
    }

    public static void close() {
        try {
            if (getTTransport() != null) {
                getTTransport().close();
            }
            remove();
        } catch (Exception e) {
            logger.error("close transport error", e);
        }
    }


}
