package task;

import java.util.concurrent.Callable;

public class Task2 implements Callable<String> {
    @Override
    public String call() {
        System.out.println("Task2 invoke");
        return "Task2";
    }
}
