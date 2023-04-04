package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            newNode.next = null;
            newNode.prev = null;
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            newNode.next = null;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of LinkedList!!!");
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            if (head == null) {
                newNode.next = null;
                newNode.prev = null;
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                newNode.prev = null;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) {
            newNode.next = null;
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node currentNode = getNodeByIndex(index - 1);
            newNode.next = currentNode.next;
            newNode.prev = currentNode;
            currentNode.next = newNode;
            currentNode = newNode.next;
            currentNode.prev = newNode;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        final T element = node.item;
        unlink(node);
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        if (size == 0) {
            return false;
        } else {
            while (node != null) {
                if (node.item == object || node.item != null && node.item.equals(object)) {
                    unlink(node);
                    return true;
                }
                node = node.next;
            }
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> node) {
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
        if (prev == null && next == null) {
            head = null;
            tail = null;
        } else if (prev == null) {
            head = next;
            head.prev = null;
        } else if (next == null) {
            tail = prev;
            tail.next = null;
        } else {
            prev.next = next;
            next.prev = prev;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of LinkedList!!!");
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}
