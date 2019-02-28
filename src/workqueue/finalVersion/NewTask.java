package workqueue.finalVersion;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Created by qukoucai001 on 2019/2/27.
 */
public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue_final";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
                            factory.setHost("59.110.71.96");
                            factory.setUsername("qukoucai");
                            factory.setPassword("lghlmcl2yhblshqt");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            String message = String.join(" ", argv);

            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
