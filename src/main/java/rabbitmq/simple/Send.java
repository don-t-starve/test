package rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

import java.io.IOException;

public class Send {

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConn();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(RabbitMqConstants.SIMPLE_QUEUE_NAME, false, false, false, null);
            String message = "this is a simple test for rabbitMQ!";
            channel.basicPublish("", RabbitMqConstants.SIMPLE_QUEUE_NAME, null, message.getBytes());
            System.out.println("simple queue add:" + message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnectionAndChannel(connection, channel);
        }

    }

}
