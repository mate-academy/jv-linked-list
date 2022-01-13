package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        private Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Out of Bounds");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> tempNode = checkBounds(index);
            Node<T> newNode = new Node<>(null, value, tempNode);
            if (index == 0) {
                head = newNode;
                tempNode.previous = newNode;
            } else {
                newNode.previous = tempNode.previous;
                tempNode.previous.next = newNode;
                newNode.next.previous = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return checkBounds(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> tempNode = checkBounds(index);
        T oldValue = tempNode.value;
        tempNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> tempNode = checkBounds(index);
        T oldValue = tempNode.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            tempNode.next.previous = null;
            head = tempNode.next;
        } else if (index == size - 1) {
            tempNode.previous.next = null;
            tail = tempNode.previous;
        } else {
            tempNode.next.previous = tempNode.previous;
            tempNode.previous.next = tempNode.next;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null && object == tempNode.value
                    || object != null && object.equals(tempNode.value)) {
                remove(i);
                return true;
            }
            tempNode = tempNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of Bounds");
        }
        Node<T> tempNode;
        if (index <= size / 2) {
            tempNode = head;
            for (int i = 0; i != index; i++) {
                tempNode = tempNode.next;
            }
        } else {
            tempNode = tail;
            for (int i = size - 1; i != index; i--) {
                tempNode = tempNode.previous;
            }
        }
        return tempNode;
    }
}
