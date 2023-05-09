package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> tail;
    private Node<T> head;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value);
        if (head == null) {
            tail = newNode;
            head = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        size++;
        checkIndex(index);
        size--;
        Node<T> nodeForeAdd = new Node<>(value);
        if (head == null) {
            head = tail = nodeForeAdd;
        } else if (index == 0) {
            nodeForeAdd.next = head;
            head = nodeForeAdd;
        } else if (index == size) {
            nodeForeAdd.prev = tail;
            tail.next = nodeForeAdd;
            tail = nodeForeAdd;
        } else {
            insert(value,index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = getByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value,int index) {
        Node<T> current = getByIndex(index);
        Node<T> oldNode = new Node<>(current.prev, current.value, current.next);
        current.value = value;
        return oldNode.value;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getByIndex(index);
        unlink(node.value);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        return unlink(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void insert(T value,int index) {
        checkIndex(index);
        Node<T> nodeForeAdd;
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        nodeForeAdd = new Node<>(current.prev, value, current);
        current.prev.next = nodeForeAdd;
        current.prev = nodeForeAdd;
    }

    private Node<T> getByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
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

    private boolean unlink(T nodeValue) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value != null
                    && current.value.equals(nodeValue)
                    || current.value == nodeValue) {
                if (i == 0) {
                    head = head.next;
                } else if (i == size - 1) {
                    tail = tail.prev;
                } else {
                    current.next.prev = current.prev;
                    current.prev.next = current.next;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Check index " + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }

        private Node(Node<T> prev,T value) {
            this.value = value;
            this.prev = prev;
        }

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
