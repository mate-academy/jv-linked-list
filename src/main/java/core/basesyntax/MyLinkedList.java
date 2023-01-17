package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> current = new Node<>(null, value, null);
        if (head == null) {
            head = current;
            tail = current;
        } else {
            tail.next = current;
            current.prev = tail;
            tail = current;
            tail.next = null;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0) {
            checkIndex(index);
        }

        Node<T> findNode = head;
        if (findNode == null) {
            add(value);
            return;
        }
        int counter = 0;
        int maxLength = size() + 1;
        while (counter != index) {

            if (index >= maxLength) {
                checkIndex(index);
                return;
            }

            findNode = findNode.next;
            counter++;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (findNode == null) {
            add(value);
            return;
        }

        if (findNode.prev == null) {
            head = newNode;
            head.next = findNode;
            findNode.prev = head;
        } else {
            newNode.prev = findNode.prev;
            findNode.prev.next = newNode;
            findNode.prev = newNode;
            newNode.next = findNode;
        }
    }

    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> findNode = head;
        int counter = 0;

        while (findNode != null && counter != index) {
            findNode = findNode.next;
            counter++;
        }
        return findNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> findNode = head;
        int counter = 0;

        while (findNode != null && counter != index) {
            findNode = findNode.next;
            counter++;
        }
        T oldValue = findNode.value;
        findNode.value = value;
        return oldValue;
    }

    @Override
    public int size() {
        Node<T> findNode = head;
        int counter = 0;

        while (findNode != null) {
            findNode = findNode.next;
            counter++;
        }
        return counter;
    }

    @Override
    public T remove(int index) {
        Node<T> findNode = head;
        int counter = 0;

        while (findNode != null && counter != index) {
            findNode = findNode.next;
            counter++;
        }
        if (findNode == null) {
            checkIndex(index);
        }
        if (findNode.prev == null && findNode.next != null) {
            final T x = findNode.value;
            findNode = findNode.next;
            findNode.prev = null;
            findNode.next.prev = findNode;
            head = findNode;
            return x;
        }
        if (findNode.next == null && findNode.prev != null) {
            findNode.prev.next = null;
            return findNode.value;
        }
        if (findNode.prev == null) {
            head = null;
            return findNode.value;
        }

        findNode.prev.next = findNode.next;
        findNode.next.prev = findNode.prev;
        return findNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> findNode = head;
        int position = 0;

        while (findNode != null && !findNode.value.equals(object)) {
            position++;
            findNode = findNode.next;

            if (position == size()) {
                return false;
            }
            if (findNode.value == null && findNode.prev != null && findNode.next != null) {
                remove(position + 1);
                return true;
            }
            if (findNode.value == null) {
                findNode = findNode.next;
                position++;
            }
        }
        remove(position);
        return true;
    }

    private void checkIndex(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException("index: "
                    + index
                    + " is bigger than or equals size: "
                    + size());
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index:" + index + " is less than 0");
        }
    }

    @Override
    public boolean isEmpty() {
        Node<T> findNode = head;
        int counter = 0;

        while (findNode != null) {
            findNode = findNode.next;
            counter++;
        }
        return counter == 0;
    }

    static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.prev = previous;
            this.value = value;
            this.next = next;
        }
    }
}
