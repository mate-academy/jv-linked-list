package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Incorrect index: " + index);
        }
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addFirst(newNode);
        } else {
            addTo(newNode,index);
        }
    }

    private void addFirst(Node<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void addTo(Node<T> newNode,int index) {
        Node<T> current = getNodeByIndex(index);
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        return (T) current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        T removedValue = current.value;
        current.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedElement = unlink(getNodeByIndex(index));
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value != null && current.value.equals(object) || current.value == object) {
                unlink(current);
                size--;
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
            throw new ArrayIndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private T unlink(Node<T> current) {
        if (current == head) {
            head = head.next;
            return (T) current.value;
        } else if (current == tail) {
            tail = tail.prev;
            return (T) current.value;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        return (T) current.value;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current;
    }
    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value) {
            this.value = value;
        }
    }
}
