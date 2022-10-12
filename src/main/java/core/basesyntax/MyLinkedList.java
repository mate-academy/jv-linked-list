package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (head == null) {
            linkHead(value);
        } else {
            linkTail(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkHead(value);
        } else if (index == size) {
            linkTail(value);
        } else {
            checkIndexToAdd(index);
            linkBefore(value, findNode(index));
        }
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
        if (index == 0) {
            return head.item;
        } else if (index == size - 1) {
            return tail.item;
        }
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T value = node.item;
        unlink(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
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

    private void linkHead(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    private void linkTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
    }

    private void linkBefore(T value, Node<T> before) {
        Node<T> newNode = new Node<>(before.prev, value, before);
        newNode.prev.next = newNode;
        before.prev = newNode;
        newNode.next = before;
        size++;
    }

    private Node<T> findNode(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private void checkIndexToAdd(int index) {
        if (index <= 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
