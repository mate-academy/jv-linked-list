package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    private static class Node<T> {
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
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(null, value, null);
            first = newNode;

        } else {
            newNode = new Node<>(last, value, null);
            last.next = newNode;
        }
        last = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        Node<T> targetNode = findNode(index);
        Node<T> newNode = new Node(targetNode.prev, value, targetNode);
        if (targetNode.prev == null) {
            targetNode.prev = newNode;
            first = newNode;
        } else {
            targetNode.prev.next = newNode;
            targetNode.prev = newNode;
        }
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
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> targetNode = findNode(index);
        T removedValue = targetNode.value;
        targetNode.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> targetNode = findNode(index);
        unlink(targetNode);
        size--;
        return targetNode.value;
    }

    @Override
    public boolean remove(T t) {
        int index = 0;
        Node<T> targetNode;
        while (!(index >= size)) {
            targetNode = findNode(index);
            if (targetNode.value == null && t == null
                    || targetNode.value != null && targetNode.value.equals(t)) {
                unlink(targetNode);
                size--;
                return true;
            }
            index++;
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

    private Node<T> findNode(int index) {
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

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        node.prev = node.next = null;
    }
}
