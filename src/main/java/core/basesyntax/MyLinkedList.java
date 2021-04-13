package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNext = search(index);
        Node<T> currentPrevious = currentNext.previous;
        Node<T> newNode = new Node<>(currentPrevious, value, currentNext);
        if (currentPrevious != null) {
            currentPrevious.next = newNode;
        }
        currentNext.previous = newNode;
        if (index == 0) {
            head = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return search(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = search(index);
        T currentOld = current.value;
        current.value = value;
        return currentOld;
    }

    @Override
    public T remove(int index) {
        Node<T> current = search(index);
        unlink(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object || object != null && object.equals(current.value)) {
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
        if (index >= size && size != 0 || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Houston we have a BUG!");
        }
    }

    private Node<T> search(int index) {
        checkIndex(index);
        if (size / 2 >= index) {
            return searchFromHead(index);
        } else {
            return searchFromTail(index);
        }
    }

    private Node<T> searchFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.previous;
        }
        return current;
    }

    private void unlink(Node<T> current) {
        Node<T> currentNext = current.next;
        Node<T> currentPrevious = current.previous;
        if (currentPrevious == null) {
            head = currentNext;
        } else {
            currentPrevious.next = currentNext;
            current.previous = null;
        }
        if (currentNext == null) {
            tail = currentPrevious;
        } else {
            currentNext.previous = currentPrevious;
            current.next = null;
        }
        size--;
    }
}
