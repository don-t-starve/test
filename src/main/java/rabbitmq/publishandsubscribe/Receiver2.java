package rabbitmq.publishandsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class Receiver2 {
    public static void main(String[] args) {
        Connection conn = ConnectionUtil.getConn();
        Channel channel = null;
        try {
            channel = conn.createChannel();
            channel.exchangeDeclare(RabbitMqConstants.PUBLISH_SUBSCRIBE_EXCHANGE_NAME, RabbitMqConstants.PUBLISH_SUBSCRIBE_TYPE_FANOUT);
            String queueName = channel.queueDeclare().getQueue();
            System.out.println("receiver2 queueName:" + queueName);
            channel.queueBind(queueName, RabbitMqConstants.PUBLISH_SUBSCRIBE_EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("receiver2:" + message);
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
