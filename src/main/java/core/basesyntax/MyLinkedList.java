package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexInBoundsStrict(index);
        if (index == 0) {
            linkFirst(value);
        } else if (index == size) {
            linkLast(value);
        } else {
            link(value, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndexInBounds(index);
        return find(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInBounds(index);
        Node<T> node = find(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexInBounds(index);
        Node<T> node = find(index);
        T oldValue = node.value;
        unlink(node);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == node.value
                    || object != null && object.equals(node.value)) {
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

    private void checkIndexInBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
        }
    }

    private void checkIndexInBoundsStrict(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
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

    private void linkFirst(T value) {
        Node<T> node = new Node<>(null, value, head);
        if (size > 0) {
            head.prev = node;
            head = node;
        } else {
            head = tail = node;
        }
    }

    private void linkLast(T value) {
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
    }

    private void link(T value, int index) {
        Node<T> next = find(index);
        Node<T> newNode = new Node<>(null, value, next);
        newNode.prev = next.prev;
        next.prev.next = newNode;
        next.prev = newNode;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }

    private String outOfBoundsMessage(int index) {
        return "Index out of bounds " + index;
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
