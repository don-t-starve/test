package audition;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatMain {

    public static void main(String[] args) {
        List<Stat> list = new ArrayList<>();
        list.add(new Stat("2020-04-22", 1, 1));
        list.add(new Stat("2020-04-22", 2, 2));
        list.add(new Stat("2020-04-23", 1, 1));

        List<Stat> mergeList = new ArrayList<>();
        list.stream().collect(Collectors.groupingBy(Stat::getDate)).forEach((k, v) -> {
            Stat stat = new Stat();
            stat.setDate(k);
            stat.setActiveCount(v.stream().mapToInt(Stat::getActiveCount).sum());
            stat.setRegisterCount(v.stream().mapToInt(Stat::getRegisterCount).sum());
            mergeList.add(stat);
        });
        System.out.println(JSON.toJSONString(mergeList));
    }

}
