package rabbitmq.simple;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * oldApi:for com.rabbitmq.client 4.0.2
 * newApi:DefaultConsumer&DeliverCallback
 */
public class ReceiverOldApi {

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConn();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(RabbitMqConstants.SIMPLE_QUEUE_NAME, false, false, false, null);
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println("old api get message:" + message);
                }
            };
            channel.basicConsume(RabbitMqConstants.SIMPLE_QUEUE_NAME, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * rabbitMQ client 4.0.2
     */
//    private void oldApi() {
//        Connection connection = ConnectionUtil.getConn();
//        try {
//            Channel channel = connection.createChannel();
//            QueueingConsumer consumer = new QueueingConsumer(channel);
//            channel.basicConsume(QUEUE_NAME, true, consumer);
//            while (true) {
//                QueueingConsumer.Delivery nextDelivery = consumer.nextDelivery();
//                String message = new String(nextDelivery.getBody());
//                System.out.println("receiver get message:" + message);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
