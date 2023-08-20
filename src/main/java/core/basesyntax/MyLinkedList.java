package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkSizeAdd(index);
        if (index == 0 && size == 0 || index > 0 && index == size) {
            add(value);
        } else {
            link(value, index);
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
        return find(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = find(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue = find(index).value;
        unlink(find(index));
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = new Node<>(null, object, null);
        for (int i = 0; i < size; i++) {
            Node<T> node = find(i);
            if (newNode.value == node.value
                    || newNode.value != null && newNode.value.equals(node.value)) {
                unlink(node);
                size--;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    private void checkSizeAdd(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Can't add element to index " + index);
        }
    }

    private Node<T> find(int index) {
        if (index > (size >> 1)) {
            return findFromTail(index);
        }
        return findFromHead(index);
    }

    private Node<T> findFromTail(int index) {
        Node<T> node = tail;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private Node<T> findFromHead(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void link(T value, int index) {
        Node<T> next = find(index);
        Node<T> newNode = new Node<>(null, value, next);
        if (size > 0 && index == 0) {
            next.prev = newNode;
            head = newNode;
        } else {
            newNode.prev = next.prev;
            next.prev.next = newNode;
            next.prev = newNode;
        }
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            if (size > 1) {
                node.next.prev = null;
                head = node.next;
            }
        } else if (node == tail) {
            if (size > 1) {
                node.prev.next = null;
                tail = node.prev;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
