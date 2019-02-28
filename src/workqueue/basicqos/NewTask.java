package workqueue.basicqos;

import auth.Auth;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by qukoucai001 on 2019/2/27.
 */
public class NewTask {
    private final static String TASK_QUEUE_NAME = "workqueue_basicqos";
    public static void main(String[] argv) throws Exception {
//        basicqos
        ConnectionFactory factory = new ConnectionFactory();
                            factory.setHost(Auth.HOST.getValue());
                            factory.setUsername(Auth.USERNAME.getValue());
                            factory.setPassword(Auth.PASSWORD.getValue());
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
            String message = String.join(" ", argv);

            channel.basicPublish("",TASK_QUEUE_NAME, null, message.getBytes());
            System.out.println(" [basicqos] Sent '" + message + "'");

        }
    }
}
