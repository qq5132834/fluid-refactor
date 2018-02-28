package com.paic.arch.jmsService.impl;

import org.apache.activemq.broker.BrokerService;

import com.paic.arch.jmsService.IBroker;

/**
* @author 17512028610
* @description 创建、启动、关闭 brokder
* @version 创建时间：2018年2月28日 下午3:42:57
*/
public class OperateBroker implements IBroker{

    private BrokerService brokerService;
    
    private String brokerUrl;
    
    public OperateBroker(String brokerUrl){
    	this.brokerUrl = brokerUrl;
    }
    
    /**
     * 创建且启动broker
     * @throws Exception 
     * */
    @Override
	public void startupBroker() throws Exception {
    	createEmbeddedBroker(this.brokerUrl);
    	startEmbeddedBroker();
	}
    
    /**
     * 创建broker
     * */
    private void createEmbeddedBroker(String brokerUrl) throws Exception {
        brokerService = new BrokerService();
        brokerService.setPersistent(false);
        brokerService.addConnector(brokerUrl);
    }
    
    /**
     * 启动broker
     * */
    private void startEmbeddedBroker() throws Exception {
        brokerService.start();
    }
     
    @Override
    public void shutdownBroker() throws Exception {
    	
        if (brokerService == null) {
            throw new IllegalStateException("Cannot stop the broker from this API: " +
                    "perhaps it was started independently from this utility");
        }
        brokerService.stop();
        brokerService.waitUntilStopped();
        
    }

	public BrokerService getBrokerService() {
		return brokerService;
	}

    
}
