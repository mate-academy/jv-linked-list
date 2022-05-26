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
            head.previous = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        } else if (index == size) {
            tail.next = newNode;
            tail.next.previous = tail;
            tail = tail.next;
            size++;
        } else {
            Node<T> element = findNodeByIndex(index);
            element.previous.next = newNode;
            newNode.previous = element.previous;
            newNode.next = element;
            element.previous = newNode;
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
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = head;
        checkIndex(index);
        if (index == 0) {
            head = head.next;
        } else if (index == size - 1) {
            node = tail;
            tail = node.previous;
            node.previous = node.previous.previous;
        } else {
            node = findNodeByIndex(index);
            node.next.previous = node.previous;
            node.previous.next = node.next;
        }
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((object == null && node.value == null) || node.value.equals(object)) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private Node<T> findNodeByIndex(int index) {
        if (index < (size / 2)) {
            Node<T> element = head;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
            return element;
        } else {
            Node<T> element = tail;
            for (int i = size - 1; i > index; i--) {
                element = element.previous;
            }
            return element;
        }
    }
}
