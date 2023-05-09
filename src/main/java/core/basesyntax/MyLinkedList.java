package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    class Node<T> {
        Node<T> prev;
        T value;
        Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }


    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        checkIndex(size);
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }


    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            if (head == null) {
                head = tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("The list parameter cannot be null.");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removalNode = head;
        if (index == 0) {
            head = removalNode.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (index == size - 1) {
            removalNode = tail;
            tail = removalNode.prev;
            tail.next = null;
        } else {
            for (int i = 0; i < index; i++) {
                removalNode = removalNode.next;
            }
            removalNode.prev.next = removalNode.next;
            removalNode.next.prev = removalNode.prev;
        }
        size--;
        return removalNode.value;
    }

    @Override
    public boolean remove(T object) {
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        }
    }

    private void checkAddIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        }
    }
}


// test