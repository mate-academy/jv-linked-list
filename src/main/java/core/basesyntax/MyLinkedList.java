package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    private class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
            size++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> targetNode = node(index);
            Node<T> newNode = new Node<>(null, value, targetNode);
            targetNode.prev = newNode;
            first = newNode;
            size++;
            return;
        }
        Node<T> targetNode = node(index);
        Node<T> newNode = new Node(targetNode.prev, value, targetNode);
        targetNode.prev.next = newNode;
        targetNode.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            if (!add(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T get(int index) {
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> targetNode = node(index);
        T removedValue = targetNode.value;
        targetNode.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> targetNode = node(index);
        if (targetNode.prev != null) {
            targetNode.prev.next = targetNode.next;
        }
        if (targetNode.next != null) {
            targetNode.next.prev = targetNode.prev;
        }
        if (index == 0) {
            first = targetNode.next;
        }
        if (index == size - 1) {
            last = targetNode.prev;
        }
        targetNode.prev = targetNode.next = null;
        size--;
        return targetNode.value;
    }

    @Override
    public boolean remove(T t) {
        int index = 0;
        while (true) {
            if (index >= size) {
                return false;
            }
            if (node(index).value == null && t == null
                    || node(index).value.equals(t)) {
                break;
            }
            index++;
        }
        remove(index);
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

    private Node<T> node(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> entry;
        if (index < size >> 1) {
            entry = first;
            for (int i = 0; i < index; i++) {
                entry = entry.next;
            }
        } else {
            entry = last;
            for (int i = size - 1; i > index; i--) {
                entry = entry.prev;
            }
        }
        return entry;
    }
}
