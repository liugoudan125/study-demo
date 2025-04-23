package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author
 * @date 2023/4/4 10:43
 * @desc ActiveMQConsumerTest
 */
public class ActiveMQConsumerTest {


    @Test
    public void testConsumer() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", "admin", "tcp://localhost:61616"
        );
        Connection connection = connectionFactory.createConnection();
        try {

            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue("test-queue-1");
            MessageConsumer consumer = session.createConsumer(queue);
            while (true) {
                Message receive = consumer.receive();
                if (receive instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) receive;
                    System.out.printf("消费者：%s, %s%n", textMessage.getJMSMessageID(), textMessage.getText());
                }
            }
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }
}
