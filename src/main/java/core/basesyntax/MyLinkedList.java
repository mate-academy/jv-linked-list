package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final String EMPTY_LIST_MESSAGE = "List is empty";
    public static final String INVALID_INDEX_MESSAGE = "Invalid index: ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateAddIndex(index);

        if (index == size) {
            add(value);
        } else {
            Node<T> nodeAtIndex = findNodeByIndex(index);
            Node<T> node = new Node<>(nodeAtIndex.prev, value, nodeAtIndex);

            if (nodeAtIndex.prev != null) {
                nodeAtIndex.prev.next = node;
            } else {
                head = node;
            }
            nodeAtIndex.prev = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeAtIndex = findNodeByIndex(index);
        unlink(nodeAtIndex);
        return nodeAtIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = findNodeByValue(object);

        if (node != null) {
            unlink(node);
            return true;
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

    private Node<T> findNodeByIndex(int index) {
        validateIndex(index);

        if (head == null) {
            throw new NoSuchElementException(EMPTY_LIST_MESSAGE);
        }

        Node<T> node;
        if (index <= size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private Node<T> findNodeByValue(T item) {
        Node<T> node = head;
        while (node != null) {
            if (item == node.value || item != null && item.equals(node.value)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }

        Node<T> prev = node.prev;
        Node<T> next = node.next;

        head = (prev == null) ? next : head;
        tail = (next == null) ? prev : tail;

        if (prev != null) {
            prev.next = next;
            node.prev = null;
        }

        if (next != null) {
            next.prev = prev;
            node.next = null;
        }

        size--;
    }

    private void validateAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_MESSAGE + index);
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_MESSAGE + index);
        }
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E value, Node<E> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
