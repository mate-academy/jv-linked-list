package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node newNode;

        if (isEmpty()) {
            newNode = new Node<T>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node(tail.prev, value, null);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }

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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        Node<T> current = findNodeByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        Node<T> current = findNodeByIndex(index);
        T overwrittenValue = current.value;
        current.value = value;
        return overwrittenValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        Node<T> current = findNodeByIndex(index);

        if (current != null) {
            return unlinkNode(current).value;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return true;
        }
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

        while (current.next != null) {
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
