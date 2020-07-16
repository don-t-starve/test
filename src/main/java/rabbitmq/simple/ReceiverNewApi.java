package rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

/**
 * 5.7.1
 */
public class ReceiverNewApi {

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConn();
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(RabbitMqConstants.SIMPLE_QUEUE_NAME, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("new api get message:" + message);
            };
            channel.basicConsume(RabbitMqConstants.SIMPLE_QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
