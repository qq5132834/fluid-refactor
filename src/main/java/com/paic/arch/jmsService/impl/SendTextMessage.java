package com.paic.arch.jmsService.impl;

import static org.slf4j.LoggerFactory.getLogger;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;

import com.paic.arch.jmsService.ISend;
import com.paic.arch.jmsbroker.JmsMessageBrokerSupport;

/**
* @author owenhuang
* @description 发送文本信息
* @version 创建时间：2018年2月28日 下午2:16:26
*/
public class SendTextMessage extends BaseInfoAbstract implements ISend{

	private static final Logger LOG = getLogger(SendTextMessage.class);
	
	private String aDestinationName;
	
	public SendTextMessage(String destinationName,String brokerUrl){
		this.aDestinationName = destinationName;
		super.brokerUrl = brokerUrl;
	}
	
	@Override
	public boolean send(String info) {
		
		String aMessageToSend = info;
		this.sendATextMessageToDestinationAt(aDestinationName, aMessageToSend);
		
		return true;
	}
	
    /**
     * 发送消息
     * @param aDestinationName 队列名称
     * @param aMessageToSend 消息
     * */
    private JmsMessageBrokerSupport sendATextMessageToDestinationAt(String aDestinationName, final String aMessageToSend) {
        super.executeCallbackAgainstRemoteBroker(brokerUrl, aDestinationName, (aSession, aDestination) -> {
            MessageProducer producer = aSession.createProducer(aDestination);
            producer.send(aSession.createTextMessage(aMessageToSend));
            producer.close();
            return "";
        });
        
        //return this;
        return null;
    }

}
