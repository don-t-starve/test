package rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class Receiver1 {

    private static void consumerMessage(String queueName, boolean notForbidDurable) {
        Connection connection = ConnectionUtil.getConn();
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, notForbidDurable, false, false, null);
            channel.basicQos(1);
            DeliverCallback deliverCallback = (String consumerTag, Delivery delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("receiver1 get message:" + message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //consumerMessage(RabbitMqConstants.WORK_QUEUE_NAME, false);
        consumerMessage(RabbitMqConstants.WORK_DURABLE_QUEUE_NAME, true);
    }

}
