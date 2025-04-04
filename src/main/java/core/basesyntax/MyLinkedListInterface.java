package core.basesyntax;

public interface MyLinkedListInterface<T> {
    void add(T value, int i); // додає елемент у кінець списку

    void remove (int index); // видаляє елемент за індексом

    T get(int index); // повертає елемент за індексом

    int size(); // повертає розмір списку

}
