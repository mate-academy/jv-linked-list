package core.basesyntax;

import java.util.List;

public interface MyLinkedListInterface<E> {

    void add(E value);

    void addFirst(E value);

    void addLast(E value);

    E removeFirst();

    E removeLast();

    E getFirst();

    E getLast();

    boolean isEmpty();

    int size();

    void clear();

    boolean contains(E value);

    void remove(E value);
}
