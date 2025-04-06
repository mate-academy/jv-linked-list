package core.basesyntax;

public interface MyLinkedListInterface<T> {
    void add(int index, T value);// додає елемент у кінець списку

    void add(T value);

    void remove (int index); // видаляє елемент за індексом

    T get(int index);// повертає елемент за індексом

    boolean remove(T object);

    T set(int index, T value);

    int size(); // повертає розмір списку

}
