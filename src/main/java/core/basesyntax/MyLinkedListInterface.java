package core.basesyntax;

import java.util.List;

public interface MyLinkedListInterface<V> {
    void add(V value);

    void add(V value, int index);

    void addAll(List<V> list);

    V get(int index);

    V set(V value, int index);

    V remove(int index);

    boolean remove(V object);

    int size();

    boolean isEmpty();
}
