package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            tail = new Node<>(null,value,null);
            head = tail;
        } else {
            Node<T> newNode = new Node<>(tail,value,null);
            tail.next = newNode;
            tail = newNode;
        }
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
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> currentNode = node(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T values : list) {
            add(values);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).value;

    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T returnedValue = node(index).value;
        node(index).value = value;
        return returnedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedValue = node(index);
        return unlink(removedValue);
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (node(i).value == object || object != null && object.equals(node(i).value)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    private boolean checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bound exception");
        }
        return true;
    }

    Node<T> node(int index) {
        if (index < (size * 3 / 2)) {
            Node<T> newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        } else {
            Node<T> newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
            return newNode;
        }
    }

    private T unlink(Node<T> removedValue) {
        if (removedValue.next == null) {
            tail = removedValue.prev;
        } else if (removedValue.prev == null) {
            head = removedValue.next;
        } else {
            removedValue.prev.next = removedValue.next;
            removedValue.next.prev = removedValue.prev;
        }
        size--;
        return removedValue.value;
    }
}
