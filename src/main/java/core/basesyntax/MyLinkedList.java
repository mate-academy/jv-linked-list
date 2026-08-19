package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.value = null;
        node.next = null;
        node.prev = null;
        size--;
    }

    public Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        if (head == null && index == 0) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;
        } else {
            Node<T> current = getNodeByIndex(index);
            newNode.prev = current.prev;
            newNode.next = current;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> searchNode = getNodeByIndex(index);
        T oldValue = searchNode.value;
        searchNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid: " + index);
        }
        Node<T> current = getNodeByIndex(index);
        final T oldValue = current.value;
        unlink(current);
        return oldValue;
    }

    @Override
    public boolean remove(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == value
                    || current.value != null && current.value.equals(value)) {
                unlink(current);
                return true;
            }
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
}
