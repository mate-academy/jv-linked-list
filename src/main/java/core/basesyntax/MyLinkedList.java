package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, null);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> newNode = new Node<>(value, tail, null);
        Node<T> current = head;
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            if (index > 0 && index < size) {
                newNode.next = current;
                newNode.prev = current.prev;
                current.prev = newNode;
                newNode.prev.next = newNode;
            } else if (index == 0) {
                newNode.next = head;
                newNode.prev = null;
                head.prev = newNode;
                head = newNode;
            } else if (index == size) {
                tail.next = newNode;
                newNode.prev = tail;
                newNode.next = null;
                tail = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            Node<T> newNode = new Node<>(item, tail, null);
            if (tail != null) {
                tail.next = newNode;
            } else {
                head = newNode;
            }
            tail = newNode;
            size++;
        }

    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeByIndex(index);

        if (!checkIndexGet(index, node)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        return node.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);

        if (!checkIndexGet(index, node)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);

        if (!checkIndexGet(index, node)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        node.prev = null;
        node.next = null;
        size--;

        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if ((node.item == null && object == null)
                    || (node.item != null && node.item.equals(object))) {
                if (node.prev != null) {
                    node.prev.next = node.next;
                } else {
                    head = node.next;
                }

                if (node.next != null) {
                    node.next.prev = node.prev;
                } else {
                    tail = node.prev;
                }

                node.prev = null;
                node.next = null;
                size--;
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private boolean checkIndexGet(int index, Node<T> node) {
        if (index < 0 || index >= size) {
            return false;
        }
        return true;
    }

    private class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        private Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

}
