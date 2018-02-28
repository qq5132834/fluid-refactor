package com.paic.arch.jmsService;

/**
* @author owenhuang
* @description 启动、关闭 broker接口
* @version 创建时间：2018年2月28日 下午4:32:30
*/
public interface IBroker {
	
	/**
	 * 启动broker
	 * */
	public void startupBroker() throws Exception;
	
	/**
	 * 关闭broker
	 * */
	public void shutdownBroker() throws Exception;

}
