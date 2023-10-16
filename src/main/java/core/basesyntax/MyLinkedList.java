package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUND_EXCEPTION = "Index: %d out of bound!! Size: %d";
    private static final int ONE = 1;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index);
        if (index == size) {
            add(value);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else {
            Node<T> current = getNodeByIndex(index);
            newNode.previous = current.previous;
            newNode.next = current;
            current.previous.next = newNode;
            current.previous = newNode;
        }
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> removedNode = getNodeByIndex(index);
        unlink(removedNode);
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((current.value == object)
                    || (current.value != null && current.value.equals(object))) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            tail = node.previous;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index + ONE);
        Node<T> current;
        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - ONE; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format(INDEX_OUT_OF_BOUND_EXCEPTION, index, size)
            );
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        private Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
