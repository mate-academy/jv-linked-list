package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
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

    public MyLinkedList() {
        size = 0;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index " + index
                    + ". Index should be more than 0" + " and less than size.");
        }
    }

    private void addToZeroPosition(T value) {
        head = tail = new Node<>(null, value, null);
        size++;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            addToZeroPosition(value);
        } else {
            tail.next = new Node<T>(tail, value, null);
            tail = tail.next;
            size++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index" + index);
        }
        if (size == 0 || index == size) {
            add(value);
            size--;
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
        } else {
            Node<T> node = getNode(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T listNode : list) {
            add(listNode);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                T oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedElement = get(index);
        Node<T> node = getNode(index);
        if (size == 1) {
            head = tail = null;
        } else if (index == 0) {
            head = node.next;
        } else if (index == size - 1) {
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
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
}
