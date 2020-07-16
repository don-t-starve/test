package Observer;

/**
 * 观察者模式：
 * 核心：
 * 1.观察者：构造器中传入被观察者实例并调用被观察者中的添加观察者方法，
 *          将自己添加到被观察者中的观察者列表中
 * 2.被观察者：存在一个观察者列表，在需要发送通知的地方循环调用所有观察者中定义的通知方法
 *            通知方法中的类容即是在被观察者发生变化是我们需要处理的逻辑
 */
public abstract class Observer {
    public Subject subject;
    public abstract void update();
}
