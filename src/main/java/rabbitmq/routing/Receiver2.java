package rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class Receiver2 {

    private static final String[] ROUTING_KEYS = new String[]{"error"};

    public static void main(String[] args) {
        Connection conn = ConnectionUtil.getConn();
        Channel channel;
        try {
            channel = conn.createChannel();
            channel.exchangeDeclare(RabbitMqConstants.ROUTING_KEY_EXCHANGE_NAME, RabbitMqConstants.PUBLISH_SUBSCRIBE_TYPE_DIRECT);
            String queueName = channel.queueDeclare().getQueue();
            System.out.println("receiver2 queueName:" + queueName);

            for (String routingKey : ROUTING_KEYS) {
                channel.queueBind(queueName, RabbitMqConstants.ROUTING_KEY_EXCHANGE_NAME, routingKey);
            }
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("receiver2:" + message + " routingKey:" + delivery.getEnvelope().getRoutingKey());
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
