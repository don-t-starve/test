package lock;

import org.springframework.util.StringUtils;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class DBLockImpl implements Lock {

    private static final int MAX_RETRY_COUNT = 60;

    @Override
    public boolean tryLock(String lockKey) {
        System.out.println(lockKey + "尝试竞争锁！");
        try {
            Connection conn = DBUtil.getConn();
            String sqlSelect = "select lock_key from lock where lock_key = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect);
            preparedStatement.setString(1, lockKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                String sqlInsert = "insert into lock(lock_key) values (?)";
                PreparedStatement statement = conn.prepareStatement(sqlInsert);
                statement.setString(1, lockKey);
                if(statement.execute()) {
                    locks.set(lockKey);
                    System.out.println(lockKey + "获得锁！");
                    return true;
                }

            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void unLock() {
        String lockKey = locks.get();
        if (!StringUtils.isEmpty(lockKey)) {
            System.out.println(lockKey + "释放锁！");
            try {
                Connection conn = DBUtil.getConn();
                String sql = "delete from locks where lock_key = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, lockKey);
                preparedStatement.execute();
                locks.remove();
            } catch (Exception e) {
                System.err.println("connection exception!");
            }
        }
    }

    @Override
    public void lock(String lockKey) {
        int count = 0;
        while (!tryLock(lockKey) && count <= 60) {
            int time = new Random().nextInt(1000);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                System.err.println("sleep error:" + e.getMessage());
            }
            count++;
        }
        System.out.println(lockKey + "上锁成功！");
    }
}
