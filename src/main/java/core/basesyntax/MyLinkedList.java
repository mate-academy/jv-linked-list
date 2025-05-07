package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;

        if (isEmpty()) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexNotInclusive(index);

        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> current = findNodeByIndex(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexInclusive(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInclusive(index);
        Node<T> current = findNodeByIndex(index);
        T overwrittenValue = current.value;
        current.value = value;
        return overwrittenValue;
    }

    @Override
    public T remove(int index) {
        checkIndexInclusive(index);
        Node<T> current = findNodeByIndex(index);
        return unlinkNode(current).value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = findNodeByValue(object);

        if (current != null) {
            unlinkNode(current);
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

    private void checkIndexInclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private void checkIndexNotInclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> findNodeByValue(T object) {
        Node<T> current = head;

        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.value, object)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private Node<T> unlinkNode(Node<T> unlink) {
        Node<T> unlinkPrev = unlink.prev;
        Node<T> unlinkNext = unlink.next;

        if (unlinkNext == null && unlinkPrev == null) {
            tail = null;
            head = null;
        } else if (unlinkPrev == null) {
            head = unlinkNext;
            unlinkNext.prev = null;
        } else if (unlinkNext == null) {
            tail = unlinkPrev;
            unlinkPrev.next = null;
        } else {
            unlinkPrev.next = unlinkNext;
            unlinkNext.prev = unlinkPrev;
        }
        size--;
        return unlink;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
