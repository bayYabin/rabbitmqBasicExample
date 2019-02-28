package workqueue.fairdispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by qukoucai001 on 2019/2/27.
 */
public class NewTask {
    private final static String TASK_QUEUE_NAME = "workqueue_fairdispatch";
    public static void main(String[] argv) throws Exception {
//        ...
        ConnectionFactory factory = new ConnectionFactory();
                            factory.setHost("59.110.71.96");
                            factory.setUsername("qukoucai");
                            factory.setPassword("lghlmcl2yhblshqt");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
            String message = String.join(" ", argv);

            channel.basicPublish("",TASK_QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }
    }
}
