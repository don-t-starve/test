package rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class Send {

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConn();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(RabbitMqConstants.ROUTING_KEY_EXCHANGE_NAME, RabbitMqConstants.PUBLISH_SUBSCRIBE_TYPE_DIRECT);
            String message = "routing key direct produce send!";
            String routingKey = "error";
            channel.basicPublish(RabbitMqConstants.ROUTING_KEY_EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("send:" + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnectionAndChannel(connection, channel);
        }
    }

}
