
package com.paic.arch.jmsService.impl;

import static org.slf4j.LoggerFactory.getLogger;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;

/**
* @author owenhuang
* @description 消息收发抽象类
* @version 创建时间：2018年2月28日 下午2:52:32
*/
abstract class BaseInfoAbstract {
	
	private static final Logger LOG = getLogger(BaseInfoAbstract.class);
	
    protected String brokerUrl;
	
    interface JmsCallback {
        String performJmsFunction(Session aSession, Destination aDestination) throws JMSException;
    }
    
    /**
     * 
     * */
    protected String executeCallbackAgainstRemoteBroker(String aBrokerUrl, String aDestinationName, JmsCallback aCallback) {
        Connection connection = null;
        String returnValue = "";
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(aBrokerUrl);
            connection = connectionFactory.createConnection();
            connection.start();
            returnValue = executeCallbackAgainstConnection(connection, aDestinationName, aCallback);
        } catch (JMSException jmse) {
            LOG.error("failed to create connection to {}", aBrokerUrl);
            throw new IllegalStateException(jmse);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException jmse) {
                    LOG.warn("Failed to close connection to broker at []", aBrokerUrl);
                    throw new IllegalStateException(jmse);
                }
            }
        }
        return returnValue;
    }
    
    protected String executeCallbackAgainstConnection(Connection aConnection, String aDestinationName, JmsCallback aCallback) {
        Session session = null;
        try {
            session = aConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(aDestinationName);
            return aCallback.performJmsFunction(session, queue);
        } catch (JMSException jmse) {
            LOG.error("Failed to create session on connection {}", aConnection);
            throw new IllegalStateException(jmse);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException jmse) {
                    LOG.warn("Failed to close session {}", session);
                    throw new IllegalStateException(jmse);
                }
            }
        }
    }

}
