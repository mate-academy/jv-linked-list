package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
            return;
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> current = node(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = node(index);
        T oldItem = current.item;
        current.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = node(index);
        final T remove = current.item;
        if (index == 0 && size != 1) {
            head = current.next;
            current.next.prev = null;
            current.next = null;
        } else if (index == size - 1 && size != 1) {
            tail = current.prev;
            current.prev.next = null;
            current.prev = null;
        } else if (size != 1) {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current.next = null;
            current.prev = null;
        }
        current.item = null;
        size--;
        return remove;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        Node<T> previous = null;
        if (size == 1 && head.item.equals(object)) {
            head.item = null;
            head = null;
            tail = null;
            size--;
            return true;
        }
        while (current.next != null) {
            if (current.item != null && current.item.equals(object)) {
                if (current == head) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            } else if (current.item == null) {
                if (current == head) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
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

    private Node<T> node(int index) {
        Node<T> current;
        if (size / 2 >= index) {
            Node<T> previous = null;
            current = head;
            for (int i = 0; i < index; i++) {
                previous = current;
                current = current.next;
            }
        } else {
            Node<T> after = null;
            current = tail;
            for (int i = size; i > index + 1; i--) {
                after = current;
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
    }
}
