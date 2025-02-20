package core.basesyntax;

public interface MyLinkedListInterface<T> {
    void addFirst(T data);
    void addLast(T data);
    void addAt(int index, T data);
    T removeFirst();
    T removeLast();
    T removeAt(int index);
    T get(int index);
    int size();
}
