package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DEFAULT_SIZE = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = DEFAULT_SIZE;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> element = findElement(index);
            Node<T> node = new Node<>(element.prev, value, element);
            if (element.prev == null) {
                head = node;
            } else {
                element.prev.next = node;
            }
            element.prev = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> element = findElement(index);
        return element.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> element = findElement(index);
        T item = element.item;
        element.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> element = findElement(index);
        unlink(element);
        return element.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> element = head;
        for (int i = 0; i < size; i++) {
            if ((object == element.item) || (object != null && object.equals(element.item))) {
                unlink(element);
                return true;
            }
            element = element.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> findElement(int index) {
        Node<T> node = head;
        if (index < size / 2) {
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

    private void unlink(Node<T> element) {
        if (element == head) {
            head = element.next;
        } else if (element == tail) {
            tail = element.prev;
        } else {
            element.prev.next = element.next;
            element.next.prev = element.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
