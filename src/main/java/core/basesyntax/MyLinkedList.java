package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(value, head, tail);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index. Please choose index from: "
            + 0 + " to: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value, head, null);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> node = findNodeByIndex(index);
        Node<T> newNode = new Node<>(value, node, node.prev);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T settedValue = findNodeByIndex(index).value;
        findNodeByIndex(index).value = value;
        return settedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNodeByIndex(index);
        if (index == size - 1) {
            if (index == 0) {
                head = null;
                tail = null;
                size--;
                return node.value;
            }
            node.prev.next = null;
            tail = node.prev;
            size--;
            return node.value;
        }
        if (index == 0) {
            node.next.prev = null;
            head = node.next;
            size--;
            return node.value;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (findNodeByIndex(i).value == object
                    || (object != null && object.equals(findNodeByIndex(i).value))) {
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
        return size == 0;
    }

    private void isIndexExsit(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index. Please choose "
             + "index from: " + 0 + "to: " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        isIndexExsit(index);
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
}
