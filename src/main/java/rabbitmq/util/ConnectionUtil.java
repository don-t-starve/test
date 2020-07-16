package rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.util.Assert;

/**
 * rabbitMQ获取连接工具类
 * @author wangyong-java
 */
public class ConnectionUtil {

    private static final String HOST = "localhost";
    private static final int PORT = 5672;
    private static final String VIRTUAL_HOST = "/wangyong-java_vhost";
    private static final String USER_NAME = "wangyong-java";
    private static final String PASSWORD = "admin123";

    /**
     * 提供ip和端口连接rabbitMQ
     * @param host ip
     * @param port 端口
     * @return
     */
    public static Connection getConn(String host, int port) {
        Assert.notNull(host, "rabbitmq host can not be empty!");
        Assert.notNull(port, "rabbitmq port can not be empty!");
        return getConnection(host, port, VIRTUAL_HOST, USER_NAME, PASSWORD);
    }

    /**
     * 使用默认的ip和端口
     * @return
     */
    public static Connection getConn() {
        return getConnection(HOST, PORT, VIRTUAL_HOST, USER_NAME, PASSWORD);
    }

    private static Connection getConnection(String host, int port, String virtualHost, String userName, String password) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
        factory.setUsername(userName);
        factory.setPassword(password);

        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnectionAndChannel(Connection connection, Channel channel) {
        try {
            if (null != channel) {
                channel.close();
            }
            if (null != connection) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
