package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node node;
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    @Override
    public void add(T value) {
        node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
            node.index = size;
            size++;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
            node.index = size;
            size++;
        }

    }

    @Override
    public void add(T value, int index) {
        check(index);
        node = new Node(value, index);
        if (index == size) {
            add(value);
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            if (current == head) {
                current.prev = node;
                node.next = current;
                node.index = index;
                size++;
                Node<T> temp = current;
                while (temp != null) {
                    temp.index++;
                    temp = temp.next;
                }
            } else {
                current.prev.next = node;
                current.prev = node;
                node.index = size;
                size++;
                Node<T> temp = current.next;
                while (temp != null) {
                    temp.index++;
                    temp = temp.next;
                }
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            node = new Node<>(null, list.get(i), null);
            tail.next = node;
            node.prev = tail;
            tail = node;
            node.index = size;
            size++;
        }

    }

    @Override
    public T get(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        node = new Node(value, index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current = node;
        T oldItem = current.item;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        check(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.prev.next = current.next;
        current.next.prev = current.prev;
        Node<T> temp = current.next;
        while (temp != null) {
            temp.index--;
            temp = temp.next;
        }
        size--;
        T oldItem = current.item;
        return oldItem;
    }

    @Override
    public boolean remove(T object) throws NoSuchElementException {
        int index = 0;
        boolean found = false;
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            current = current.next;
            if (current.item == object || current.item != null && current.item.equals(object)) {
                index = i;
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoSuchElementException("Element " + object + " was not found");
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
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void check(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private static class Node<T> {
        private T item;
        private Node prev;
        private Node next;
        private int index;

        public Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        public Node(T item, int index) {
            this.item = item;
            this.index = index;
        }
    }
}


