package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String FORMATTED_EXCEPTION_MESSAGE = "Index %d out of bounds. Size: %d";
    private static final int NUMBER_ONE = 1;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (size == index) {
            add(value);
        } else if (index == 0) {
            newNode = new Node<>(null, value, head);
            head = newNode;
            size++;
        } else {
            checkIndex(index);
            Node<T> node = getElement(index);
            newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldElement = getElement(index).value;
        getElement(index).value = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getElement(index);
        return removeLinks(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> element = head;
        for (int i = 0; i < size; i++) {
            if ((element.value == object)
                    || (element.value != null && element.value.equals(object))) {
                removeLinks(element);
                return true;
            }
            element = element.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format(FORMATTED_EXCEPTION_MESSAGE, index, size));
        }
    }

    private Node<T> getElement(int index) {
        Node<T> element;
        if (index < size >> NUMBER_ONE) {
            element = head;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
        } else {
            element = tail;
            for (int i = size - NUMBER_ONE; i > index; i--) {
                element = element.prev;
            }
        }
        return element;
    }

    private T removeLinks(Node<T> node) {
        if (node.next == null && node.prev == null) {
            head = tail = null;
        } else if (node.prev == null) {
            head = head.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
