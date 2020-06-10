package lock;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class ZKLockImpl implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(ZKLockImpl.class);

    private ZooKeeper zooKeeper;

    private static final String ZK_CONNECT_ADDR = "localhost:2181";
    private static final int SESSION_OUTTIME = 60000;
    private static final String ROOT_PATH = "/myLocks";
    private static final String PATH_SPILT = "/";
    private static final int VERSION = -1;

    public ZKLockImpl() {
        try {
            zooKeeper = new ZooKeeper(ZK_CONNECT_ADDR, SESSION_OUTTIME, new LockWatcher(new CountDownLatch(1)));
            Stat stat = zooKeeper.exists(ROOT_PATH, false);
            if (null == stat) {
                zooKeeper.create(ROOT_PATH, "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            logger.error("can not connect to zookeeper or fail to create root node:", e);
        }
    }

    @Override
    public boolean tryLock(String lockKey) {
        String nodePath = ROOT_PATH + PATH_SPILT + lockKey;
        try {
            Stat stat = zooKeeper.exists(nodePath, new LockWatcher(new CountDownLatch(1)));
            if (null == stat) {
                zooKeeper.create(nodePath, "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String currentNode = zooKeeper.create(nodePath + PATH_SPILT + "Lock", "2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.info("节点{}尝试竞争锁！", currentNode);
            List<String> nodes = zooKeeper.getChildren(nodePath, false);
            if (!CollectionUtils.isEmpty(nodes)) {
                nodes.sort(String::compareTo);
                if (currentNode.equals(nodePath + PATH_SPILT + nodes.get(0))) {
                    locks.set(currentNode);
                    logger.info("节点{}获得锁！", currentNode);
                    return true;
                }
            }
            logger.info("tryLock nodes:{}" + JSON.toJSONString(nodes));
            //若不是最小节点，则监控当前节点的上一个节点
            String node = currentNode.substring(currentNode.indexOf(PATH_SPILT) + 1);
            String preNode = nodes.get(Collections.binarySearch(nodes, node) - 1);
            logger.info("当前节点:{} 上一节点:{}", currentNode, preNode);
            CountDownLatch latch = new CountDownLatch(1);
            Stat stat1 = zooKeeper.exists(nodePath + PATH_SPILT + preNode, new LockWatcher(new CountDownLatch(1)));
            if (null != stat1) {
                logger.info("节点:{}等待！", currentNode);
                latch.await();
                locks.set(currentNode);
            }
            logger.info("节点:{}获得锁！", currentNode);
            return true;
        } catch (Exception e) {
            logger.error("zookeeper try to lock error:", e);
        }
        return false;
    }

    @Override
    public void unLock() {
        String currentNodeId = locks.get();
        if (StringUtils.isEmpty(currentNodeId)) {
            logger.info("当前节点:{}释放锁！", currentNodeId);
            try {
                zooKeeper.delete(currentNodeId, VERSION);
            } catch (Exception e) {
                logger.error("zookeeper delete node error:", e);
            } finally {
                locks.remove();
            }
        }
    }

    @Override
    public void lock(String lockKey) {

    }
}
