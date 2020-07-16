package Observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private int state;

    private List<Observer> observers = new ArrayList<Observer>();

    public void attachObserver(Observer observer) {
        observers.add(observer);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObserver();
    }

    private void notifyAllObserver() {
        observers.forEach(observer -> observer.update());
    }
}
