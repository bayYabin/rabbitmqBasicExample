package workqueue.roundrobin;

import auth.Auth;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * Created by qukoucai001 on 2019/2/27.
 */
public class Worker {
    private final static String TASK_QUEUE_NAME = "workqueue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
                            factory.setHost(Auth.HOST.getValue());
                            factory.setUsername(Auth.USERNAME.getValue());
                            factory.setPassword(Auth.PASSWORD.getValue());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }finally {
                System.out.println(" [x] Done");
            }
        };
        boolean autoAck = true; // acknowledgment is covered below
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });

    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.'){
                Thread.sleep(1000);
            }
        }
    }
}
