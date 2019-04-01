package cn.itcast.core.listener;

import cn.itcast.core.service.StaticPageService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class PageDeleteListener implements MessageListener {
    @Autowired
    private StaticPageService staticPageService;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage atm = (ActiveMQTextMessage) message;
        try {
            String id = atm.getText();

            System.out.println("静态化项目删除时:接收到的Id:" + id);

            //3:删除静态化
            staticPageService.delete(Long.parseLong(id));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
