package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head; //початок
    private Node<T> tail; //кінець
    private int size = 0;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
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
        checkIndex(index);
        Node<T> current = search(index);
        T currentOld = current.value;
        current.value = value;
        return currentOld;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = search(index);
        Node<T> currentNext = current.next;
        Node<T> currentPrev = current.prev;
        unlink(currentPrev, currentNext, index);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object || object != null && object.equals(current.value)) {
                Node<T> currentNext = current.next;
                Node<T> currentPrev = current.prev;
                unlink(currentPrev, currentNext, i);
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

    private void unlink(Node<T> currentPrev, Node<T> currentNext, int index) {
        if (index == 0) {
            head = currentNext;
        }
        if (index == size - 1) {
            tail = currentPrev;
        }
        if (currentPrev != null) {
            currentPrev.next = currentNext;
        }
        if (currentNext != null) {
            currentNext.prev = currentPrev;
        }
        size--;
    }
}
