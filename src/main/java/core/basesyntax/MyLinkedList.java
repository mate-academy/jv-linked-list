package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        Node<T> foundNode = getNode(index);
        if (foundNode != null) {
            Node<T> prev = foundNode.prev;
            Node<T> newNode = new Node<>(prev, value, foundNode);
            foundNode.prev = newNode;
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        } else {
            add(value);
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
        Node<T> node = getNode(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndex(object);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of bounds!");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> node = head;
        if (size != 0) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    private void checkIndexAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of bounds!");
        }
    }

    private int getIndex(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == value
                    || node.item != null && node.item.equals(value)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            tail = next;
        } else {
            next.prev = previous;
        }
        node.prev = null;
        node.next = null;
        size--;
    }
}
