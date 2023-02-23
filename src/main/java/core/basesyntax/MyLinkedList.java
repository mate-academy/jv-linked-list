package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = new Node<>(null, value, null);
        if (index == size) {
            add(value);
            return;
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index - " + index
                    + " out of bounds for length: " + size);
        }
        if (index == 0) {
            node.prev = null;
            node.next = head;
            head.prev = node;
            head = node;
        } else {
            Node<T> temp = getNode(index);
            node.prev = temp.prev;
            node.next = temp;
            temp.prev.next = node;
            temp.prev = node;
        }
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
        return getNode(index).currentValue;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T newValue = node.currentValue;
        node.currentValue = value;
        return newValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.currentValue;
        unlink(node);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (object == node.currentValue || object != null && object.equals(node.currentValue)) {
                unlink(node);
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node = head;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size; i > index + 1; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index - " + index
                    + " out of bounds for length: " + size);
        }
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            if (size != 1) {
                head.next.prev = null;
                head = head.next;
            } else {
                head = null;
                tail = null;
            }
            size--;
            return;
        }
        if (node == tail) {
            tail.prev.next = null;
            tail = tail.prev;
            size--;
            return;
        }
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T currentValue;

        Node(MyLinkedList.Node<T> prev, T currentValue, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.currentValue = currentValue;
        }
    }
}
