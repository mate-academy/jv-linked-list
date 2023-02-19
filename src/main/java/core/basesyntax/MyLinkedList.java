package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
            size++;
        } else {
            Node<T> current = new Node<>(getNode(index).prev, value, getNode(index));
            current.next.prev = current;
            current.prev.next = current;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = getNode(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        T replacedValue = getNode(index).value;
        getNode(index).value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNode(index);
        T removedValue = currentNode.value;
        if (size == 1) {
            makeEmpty();
            return removedValue;
        }
        if (index == size - 1) {
            tail = currentNode.prev;
            currentNode.prev.next = null;
        } else {
            currentNode.next.prev = currentNode.prev;
        }
        if (index == 0) {
            head = currentNode.next;
            currentNode.next.prev = null;
        } else {
            currentNode.prev.next = currentNode.next;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.value, object)) {
                remove(i);
                return true;
            }
            current = current.next;
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

    private void makeEmpty() {
        head = null;
        tail = null;
        size = 0;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index == 0) {
            return head;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        return current.next;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size || isEmpty()) {
            throw new IndexOutOfBoundsException(index
                    + " is invalid index for " + size);
        }
    }
}
