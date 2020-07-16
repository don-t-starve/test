package rabbitmq.workqueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import rabbitmq.constants.RabbitMqConstants;
import rabbitmq.util.ConnectionUtil;

/**
 * work queue use a round-robin way to process tasks
 * 轮询分发：当同时有很多任务在队列中时，tasks将会被平均到所有的消费者
 *          不考虑消费者的性能
 * 问题：消费者采用自动应答的机制，当任务分发到消费者后即从队列中删除task或者message，
 *       当消费者出现异常时，task或者message会丢失
 *       异常包括：channel is closed, connection is closed, or TCP connection is lost
 * 公平分发：关闭消费者的自动应答机制，生产者分发任务之后不会直接在内存中删除tasks and message,
 *          直到消费者处理完任务之后才给生产者发送应答消息，生产者收到消费者的应答之后才会
 *          将删除tasks and message，考虑消费者的性能问题（能者多劳）。
 *          即使消费者挂掉了也不会出现消息丢失的问题，如果其中一个消费者挂掉了，当前的消息会被
 *          提供到其他消费者处理。
 * durable：持久化
 *          rabbitMQ-server重启(rabbitmq-server restart)或宕机都会使消息丢失，需要持久化数据到硬盘
 */
public class Send {

    /**
     * 当未开启持久化时，如果mq-server挂掉了，那么内存中的消息都会被清除，因此需要开启对server消息的持久化
     * @param queueName 队列名
     * @param notForbidToDurable 不禁止持久化
     */
    private static void queueForDurable(String queueName, boolean notForbidToDurable) {
        Connection connection = ConnectionUtil.getConn();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(queueName, notForbidToDurable, false, false, null);
            for (int i = 0; i < 50; i++) {
                String message = "this is a work queue for rabbitMQ!" + i;

                AMQP.BasicProperties properties = null;
                if (notForbidToDurable) {
                    properties = MessageProperties.PERSISTENT_TEXT_PLAIN;
                }
                channel.basicPublish("", queueName, properties, message.getBytes());
                System.out.println("work queue add:" + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnectionAndChannel(connection, channel);
        }
    }

    public static void main(String[] args) {
        //非持久化队列
        queueForDurable(RabbitMqConstants.WORK_QUEUE_NAME, false);
        //持久化队列
        //queueForDurable(RabbitMqConstants.WORK_DURABLE_QUEUE_NAME, true);
    }

}
