package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> oldNode = getNode(index);
        Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
        if (index == 0) {
            head = newNode;
        } else {
            oldNode.prev.next = newNode;
        }
        oldNode.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNode(index);
        T oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> oldNode = getNode(index);
        T value = oldNode.value;
        if (index == 0 && oldNode.next != null) {
            oldNode.next.prev = null;
            head = oldNode.next;
        } else if (oldNode.next != null && oldNode.prev != null) {
            oldNode.next.prev = oldNode.prev;
            oldNode.prev.next = oldNode.next;
        } else if (oldNode.prev != null) {
            oldNode.prev.next = null;
            tail = oldNode.prev;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> oldNode = head;
        for (int i = 0; i < size; i++) {
            if (oldNode.value == object
                    || (oldNode.value != null && oldNode.value.equals(object))) {
                remove(i);
                return true;
            }
            oldNode = oldNode.next;
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

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index >= 0 && index < size) {
            if (size / 2 >= index) {
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.prev;
                }
            }
            return current;
        }
        throw new IndexOutOfBoundsException("List doesn't have this index " + index);
    }
}
