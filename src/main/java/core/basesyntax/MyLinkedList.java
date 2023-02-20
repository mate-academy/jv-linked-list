package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int length;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<T>(tail, value, null);
            tail = tail.next;
        }
        length++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("From add Index " + index + " is out of scope");
        }
        if (index == length) {
            add(value);
            return;
        }
        Node<T> after = getNode(index);
        Node<T> before = after.prev;
        Node<T> newNode = new Node<T>(before, value, after);
        after.prev = newNode;
        if (before != null) {
            before.next = newNode;
        } else {
            head = newNode;
        }
        length++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> elementToChange = getNode(index);
        T oldItem = elementToChange.item;
        elementToChange.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> elementToRemove = getNode(index);
        unlink(elementToRemove);
        return elementToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < length; i++) {
            if (current.item == object
                    || current.item != null
                    && current.item.equals(object)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("From add Index " + index + " is out of scope");
        }
        Node<T> current;
        if ((length / 2) > index) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = length; i > (index + 1); i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        length--;
        if (node.next == null) {
            if (node.prev != null) {
                node.prev.next = null;
            }
            tail = node.prev;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
