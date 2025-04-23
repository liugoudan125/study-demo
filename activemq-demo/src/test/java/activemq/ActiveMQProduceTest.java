package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author
 * @date 2023/4/4 10:30
 * @desc ActiveMQProduceTest
 */
public class ActiveMQProduceTest {

    @Test
    public void testProduceM() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", "admin", "tcp://localhost:61616"
        );
        Connection connection = connectionFactory.createConnection();
        try {

            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue("test-queue-1");
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            for (; ; ) {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("text_" + System.currentTimeMillis());
                producer.send(textMessage);
                System.out.println("生产者：" + textMessage.getText());
                Thread.sleep(2000L);
            }
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }
}
