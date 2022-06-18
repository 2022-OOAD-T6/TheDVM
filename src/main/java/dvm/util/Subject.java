package dvm.util;

public interface Subject {

    void registerObserver(Observer observer);

    void notifyObservers(String itemCode, int quantity);
}
