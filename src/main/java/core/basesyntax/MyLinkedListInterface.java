package core.basesyntax;

import java.util.List;

public interface MyLinkedListInterface<M> {
    void add(M value);

    void add(M value, int index);

    void addAll(List<M> list);

    M get(int index);

    M set(M value, int index);

    M remove(int index);

    boolean remove(M object);

    int size();

    boolean isEmpty();
}
