package com.paic.arch.jmsService.impl;

import static org.slf4j.LoggerFactory.getLogger;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import org.slf4j.Logger;

import com.paic.arch.jmsService.IReceive;

/**
* @author owenhuang
* @description 接收文本信息
* @version 创建时间：2018年2月28日 下午2:16:08
*/
public class ReceiveTextMessage extends BaseInfoAbstract implements IReceive{
	
	private static final Logger LOG = getLogger(ReceiveTextMessage.class);
	
	private String aDestinationName;
	private int timeout;
	
	/**
	 * 构造函数
	 * @param destinationName 队名称
	 * @param brokerUrl 
	 * @param timeout 超时时间
	 * */
	public ReceiveTextMessage(String destinationName,String brokerUrl, int timeout){
		this.aDestinationName = destinationName;
		this.timeout = timeout;
		super.brokerUrl = brokerUrl;
	}

	@Override
	public String receive() {
		return this.retrieveASingleMessageFromTheDestination(aDestinationName, timeout);
	}

	
    /**
     * 接收消息
     * @param aDestinationName 队列名称
     * @param aTimeout 超时时间
     * */
    private String retrieveASingleMessageFromTheDestination(String aDestinationName, final int aTimeout) {
    	
        return super.executeCallbackAgainstRemoteBroker(brokerUrl, aDestinationName, (aSession, aDestination) -> {
            MessageConsumer consumer = aSession.createConsumer(aDestination);
            Message message = consumer.receive(aTimeout);
            if (message == null) {
                throw new NoMessageReceivedException(String.format("No messages received from the broker within the %d timeout", aTimeout));
            }
            consumer.close();
            return ((TextMessage) message).getText();
        });
    }
    
    
    public class NoMessageReceivedException extends RuntimeException {
        public NoMessageReceivedException(String reason) {
            super(reason);
        }
    }
}
