package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> currentNext = search(index);
        Node<T> currentPrev = currentNext.prev;
        Node<T> newTail = new Node<>(currentPrev, value, currentNext);
        if (currentPrev != null) {
            currentPrev.next = newTail;
        }
        currentNext.prev = newTail;
        if (index == 0) {
            head = newTail;
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
        Node<T> node = search(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = search(index);
        T currentFirst = current.value;
        current.value = value;
        return currentFirst;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(search(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (object == current.value || object != null
                    && object.equals(current.value)) {
                unlink(current);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " out of bounds");
        }
    }

    private Node<T> search(int index) {
        checkIndex(index);
        Node<T> current;
        if (size / 2 > index) {
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

    private T unlink(Node<T> current) {
        Node<T> currentNext = current.next;
        Node<T> currentPrev = current.prev;
        if (currentPrev != null) {
            currentPrev.next = currentNext;
            current.prev = null;
        } else {
            head = currentNext;
        }
        if (currentNext != null) {
            currentNext.prev = currentPrev;
            current.next = null;
        } else {
            tail = currentPrev;
        }
        size--;
        return current.value;
    }
}
