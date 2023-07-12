package core.basesyntax;

import java.util.ArrayList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String NEGATIVE_INDEX_MESSAGE = "Index must be positive number";
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index must be positive number";
    private final ArrayList<Node<T>> data = new ArrayList<>();
    private int size;

    private static class Node<V> {
        private V value;
        private Node<V> next;
        private Node<V> prev;

        Node(Node<V> prev, V value, Node<V> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        data.add(new Node<>((size == 0) ? null : data.get(size - 1), value, null));
        if (size > 0) {
            data.get(size - 1).next = data.get(size);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexValidator(index, false);
        data.add(index, new Node<>(
                (index > 0) ? data.get(index - 1) : null,
                value,
                (size > 0 && index < size - 1) ? data.get(index) : null));
        if (index > 0) {
            data.get(index - 1).next = data.get(index);
        }
        if (index < size) {
            data.get(index + 1).prev = data.get(index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        int newSize = size + list.size() * 2;
        for (int i = size; i < newSize; i = i + 2) {
            add(list.get(i - size));
        }
    }

    @Override
    public T get(int index) {
        indexValidator(index, true);
        return data.get(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexValidator(index, false);
        T oldValue = data.get(index).value;
        data.get(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexValidator(index, true);
        if (index > 0 && index < size - 1) {
            data.get(index - 1).next = data.get(index + 1);
            data.get(index + 1).prev = data.get(index - 1);
        } else if (index == 0 && size > 1) {
            data.get(index + 1).prev = null;
        } else if (index > 0 && index == size - 1) {
            data.get(index - 1).next = null;
        }
        size--;
        return data.remove(index).value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((data.get(i).value != null && data.get(i).value.equals(object))
                    || (data.get(i).value == object)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void indexValidator(int index, boolean deleteContacted) {
        if (!deleteContacted && index > size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        } else if (deleteContacted && index >= size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        } else if (index < 0) {
            throw new IndexOutOfBoundsException(NEGATIVE_INDEX_MESSAGE);
        }
    }
}
