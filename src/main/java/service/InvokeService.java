package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * spring批注入
 * 1.list接收：如所有的实现类都需要执行则可用list
 * 2.map接收：如需指定对应的实现类调用则用map，map的key为component指定的value，未指定则为类名的驼峰
 * 3.构造器注入：入参形式传入，传入得对象必须为spring管理的对象
 */
@Component
public class InvokeService {

    @Autowired
    private List<BatchInService> batchInServiceList;
    @Autowired
    private Map<String, BatchInService> batchInServiceMap;

    private List<BatchInService> list;

    public InvokeService(List<BatchInService> list) {
        this.list = list;
    }

}
