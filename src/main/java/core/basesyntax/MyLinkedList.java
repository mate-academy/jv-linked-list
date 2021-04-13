package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int HALF_OF_SIZE = 2;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        return addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index <= size && index >= 0) {
            if (index == 0) {
                addFirst(value);
            } else if (index == size) {
                addLast(value);
            } else {
                insert(getByIndex(index), value);
            }
        } else {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            addLast(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < size && index >= 0) {
            return getByIndex(index).item;
        }
        throw new IndexOutOfBoundsException("Index is out of bounds");
    }

    @Override
    public T set(T value, int index) {
        T previousValue;
        if (index < size && index >= 0) {
            previousValue = getByIndex(index).item;
            getByIndex(index).item = value;
            return previousValue;
        }
        throw new IndexOutOfBoundsException("Index is out of bounds");
    }

    @Override
    public T remove(int index) {
        if (index < size && index >= 0) {
            T removedElement;
            if (index == 0) {
                removedElement = head.item;
                removeFirst();
                return removedElement;
            } else if (index == size - 1) {
                removedElement = tail.item;
                removeLast();
                return removedElement;
            }
            return removeElement(getByIndex(index));
        }
        throw new IndexOutOfBoundsException("Index is out of bounds");
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current.item == null ? !(current.item == object)
                : !current.item.equals(object)) {
            if (current.next == null) {
                return false;
            }
            current = current.next;
        }
        if (current == head) {
            removeFirst();
            return true;
        }
        removeElement(current);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean addLast(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return true;
        }
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
        return true;
    }

    private void addFirst(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(null, value, head);
        head.previous = newNode;
        head = newNode;
        size++;
    }

    private void insert(Node<T> current, T value) {
        Node<T> newElement = new Node<>(current.previous, value, current);
        current.previous.next = newElement;
        current.previous = newElement;
        size++;
    }

    private Node<T> getByIndex(int index) {
        if (index <= size / HALF_OF_SIZE) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.previous;
        }
        return current;
    }

    private T removeElement(Node<T> current) {
        current.previous.next = current.next;
        current.next.previous = current.previous;
        size--;
        return current.item;
    }

    private void removeFirst() {
        if (head.next == null) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        size--;
    }

    private void removeLast() {
        if (tail.previous == null) {
            head = null;
            tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }
        size--;
    }
}
