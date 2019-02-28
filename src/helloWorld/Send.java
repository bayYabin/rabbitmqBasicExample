package helloWorld;

import auth.Auth;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by qukoucai001 on 2019/2/27.
 */
public class Send {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {
//        ...
        ConnectionFactory factory = new ConnectionFactory();
                            factory.setHost(Auth.HOST.getValue());
                            factory.setUsername(Auth.USERNAME.getValue());
                            factory.setPassword(Auth.PASSWORD.getValue());
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "10";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }
    }
}
