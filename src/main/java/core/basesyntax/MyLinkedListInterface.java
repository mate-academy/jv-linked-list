package core.basesyntax;

import java.util.List;

public interface MyLinkedListInterface<T> {
    void add(T value);

    void add(T value, int index);

    void add(int index, T element);

    void addAll(List<T> list);

    T get(int index);

    T set(T value, int index);

    T set(int index, T element);

    T remove(int index);

    boolean remove(T object);

    int size();

    boolean isEmpty();
}
