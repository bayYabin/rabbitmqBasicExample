package workqueue.messagedurability;

import auth.Auth;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Created by qukoucai001 on 2019/2/27.
 */
public class NewTask {
    private final static String TASK_QUEUE_NAME = "workqueue_messagedurability";
    public static void main(String[] argv) throws Exception {
//        ...
        ConnectionFactory factory = new ConnectionFactory();
                            factory.setHost(Auth.HOST.getValue());
                            factory.setUsername(Auth.USERNAME.getValue());
                            factory.setPassword(Auth.PASSWORD.getValue());
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            boolean durable = true;
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
            String message = String.join(" ", argv);

            /*
            Now we need to mark our messages as persistent -
            by setting MessageProperties (which implements BasicProperties) to the value PERSISTENT_TEXT_PLAIN.
             */
            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
            System.out.println(" [messagedurability] Sent '" + message + "'");

            /*
            Note on message persistence
Marking messages as persistent doesn't fully guarantee that a message won't be lost. Although it tells RabbitMQ to save the message to disk, there is still a short time window when RabbitMQ has accepted a message and hasn't saved it yet. Also, RabbitMQ doesn't do fsync(2) for every message -- it may be just saved to cache and not really written to the disk. The persistence guarantees aren't strong, but it's more than enough for our simple task queue. If you need a stronger guarantee then you can use publisher confirms.
             */

        }
    }
}
