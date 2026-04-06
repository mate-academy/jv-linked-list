package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node first;
    private Node last;

    public MyLinkedList() {
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public void add(T value) {
        Node newNode = new Node<T>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;

    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node theNode = findNode(index);
        Node newNode = new Node<T>(theNode.prev, value, theNode);
        if (index != 0) {
            newNode.prev.next = newNode;
        }
        newNode.next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return (T) findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node x = findNode(index);
        T oldValue = (T) x.item;
        x.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node x = findNode(index);
        T theItem = (T) x.item;
        unlink(x);
        return theItem;
    }

    @Override
    public boolean remove(T object) {
        for (Node node = first; node != null; node = node.next) {
            if (object == null ? node.item == null : object.equals(node.item)) {
                unlink(node);
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

    private Node findNode(int index) {
        Node x = first;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }

    private void unlink(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        if (node.prev == null) {
            first = node.next;
        } else {
            prev.next = node.next;
            node.prev = null;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            next.prev = node.prev;
            node.next = null;
        }
        node.item = null;
        size--;
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
}
