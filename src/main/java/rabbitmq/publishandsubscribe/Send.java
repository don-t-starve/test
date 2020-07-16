package rabbitmq.publishandsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

/**
 * 生产者无需定义队列，只需生产消息到交换器
 * 消费者使用客户端默认的队列生成，绑定到交换器上
 * 生产者发布消息到交换机之后，所有的队列中均有此消息，所有的消费者都会收到此消息
 */
public class Send {

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConn();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(RabbitMqConstants.PUBLISH_SUBSCRIBE_EXCHANGE_NAME, RabbitMqConstants.PUBLISH_SUBSCRIBE_TYPE_FANOUT);
            String message = "publish and subscribe produce send!";
            channel.basicPublish(RabbitMqConstants.PUBLISH_SUBSCRIBE_EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("send:" + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnectionAndChannel(connection, channel);
        }
    }

}
