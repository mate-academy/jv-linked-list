package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX = "Index: ";
    private static final String SIZE = ", Size: ";
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int DIVORCE_NUMBER = 2;

    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < ZERO || index > size) {
            throw new IndexOutOfBoundsException(INDEX + index + SIZE + size);
        }
        if (size == index) {
            add(value);
        } else {
            Node<T> adding = new Node<>(value);
            if (index == ZERO) {
                adding.next = head;
                head.prev = adding;
                head = adding;
            } else {
                Node<T> before = getNode(index - ONE);
                adding.next = before.next;
                adding.prev = before;
                before.next.prev = adding;
                before.next = adding;
            }
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> item = getNode(index);
        T oldDate = item.value;
        item.value = value;
        return oldDate;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        unlink(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(object, currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == ZERO;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / DIVORCE_NUMBER) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - ONE; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> unlinkedNode) {
        if (unlinkedNode == head) {
            head = unlinkedNode.next;
        } else {
            unlinkedNode.prev.next = unlinkedNode.next;
        }
        if (unlinkedNode == tail) {
            tail = unlinkedNode.prev;
        } else {
            unlinkedNode.next.prev = unlinkedNode.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < ZERO || index >= size) {
            throw new IndexOutOfBoundsException(INDEX + index + SIZE + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }
}
