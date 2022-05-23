package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail.next.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException("The index does not exist");
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = newNode;
            size++;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
            size++;
        } else if (index == size) {
            tail.next = newNode;
            tail = tail.next;
            size++;
        } else {
            Node<T> element = head;
            element = passageElements(index);
            element.previous.next = newNode;
            newNode.next = element;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> element = head;
        checkIndex(index);
        element = passageElements(index);
        return element.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = head;
        checkIndex(index);
        element = passageElements(index);
        Node<T> oldElement = new Node<>(null, element.value, null);
        element.value = value;
        return oldElement.value;
    }

    @Override
    public T remove(int index) {
        Node<T> element = head;
        checkIndex(index);
        if (index == 0) {
            head = head.next;
            size--;
        } else if (index == size - 1) {
            element = tail;
            size--;
        } else {
            element = passageElements(index);
            element.next.previous = element.previous;
            element.previous.next = element.next;
            size--;
        }
        return element.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> element = head;
        for (int i = 0; i < size; i++) {
            if ((object == null && element.value == null) || element.value.equals(object)) {
                remove(i);
                return true;
            }
            element = element.next;
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

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("The index does not exist");
        }
    }

    private Node<T> passageElements(int index) {
        Node<T> element = head;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        return element;
    }
}
