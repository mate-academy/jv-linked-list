package core.basesyntax;

import java.util.List;

public interface MyLinkedListInterface<T> {
    void add(T value);

    void add(int index, T value);

    void addAll(List<T> list);

    T get(int index);

    T set(T value, int index);

    T remove(int index);

    boolean remove(T object);

    int size();

    boolean isEmpty();
}
