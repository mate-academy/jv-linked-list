package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.head = newNode;
            newNode.tail = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throwOutOfBoundsException(index);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        Node<T> current = getNodeAtIndex(index);

        if (index == 0) {
            first = newNode;
        } else {
            newNode.tail = current.tail;
            current.tail.head = newNode;
        }

        newNode.head = current;
        current.tail = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNodeAtIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> current = getNodeAtIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);

        Node<T> current = getNodeAtIndex(index);
        if (index == 0) {
            first = current.head;
        } else {
            current.tail.head = current.head;
        }
        if (index == size - 1) {
            last = current.tail;
        } else {
            current.head.tail = current.tail;
        }

        current.tail = null;
        current.head = null;

        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (Objects.equals(object, current.value)) {
                if (current == first) {
                    first = current.head;
                } else {
                    current.tail.head = current.head;
                }
                if (current == last) {
                    last = current.tail;
                } else {
                    current.head.tail = current.tail;
                }
                current.tail = null;
                current.head = null;
                size--;
                return true;
            }
            current = current.head;
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

    private Node<T> getNodeAtIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.head;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.tail;
            }
        }
        return current;
    }

    private void throwOutOfBoundsException(int index) {
        throw new IndexOutOfBoundsException("Index is out of bounds, given index: " + index);
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throwOutOfBoundsException(index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> head;
        private Node<T> tail;

        private Node(T value) {
            this.value = value;
        }
    }
}
