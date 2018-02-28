### Candidate Chinese Name:
* 
 黄聊
- - -  
### Please write down some feedback about the question(could be in Chinese):
* 
重构JmsMessageBrokerSupport.java类。因为此类中含有5个功能，分别是：
1、启动broker；
2、关闭broker；
3、发送消息；
4、接收消息；
5、获取数量；
重构了其中1/2/3/4项。分别在重构在IBroker、IReceive、ISend接口的实现类中，和BaseInfoAbstract抽象类中。

Junit测试无误。
- - -