package task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class TaskMain {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<FutureTask> taskList = new ArrayList<>();
        Task1 task1 = new Task1();
        Task2 task2 = new Task2();
        FutureTask<String> futureTask1 = new FutureTask<>(task1);
        FutureTask<String> futureTask2 = new FutureTask<>(task2);
        taskList.add(futureTask1);
        taskList.add(futureTask2);
        executor.submit(futureTask1);
        executor.submit(futureTask2);
        try {
            String str1 = futureTask1.get();
            String str2 = futureTask2.get();
            System.out.println(str1 + "---" + str2);
        } catch (Exception e) {
            System.err.println("futureTask get error!");
        }
        executor.shutdown();
    }

}
