package task;

import java.util.concurrent.Callable;

public class Task1 implements Callable<String> {
    @Override
    public String call() {
        System.out.println("Task1 invoke");
        return "Task1";
    }
}
