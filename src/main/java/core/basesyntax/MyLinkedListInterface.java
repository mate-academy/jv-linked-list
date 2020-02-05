package core.basesyntax;

import java.util.List;

public interface MyLinkedListInterface<T> {

    boolean add(T value);

    void add(T value, int index);

    boolean addAll(List<T> list);

    T get(int index);

    T set(T value, int index);

    T remove(int index);

    boolean remove(T t);

    int size();

    boolean isEmpty();
}
