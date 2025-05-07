package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, head);
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            Node<T> nextNode = prev.next;
            prev.next = newNode;
            nextNode.prev = newNode;
            newNode.next = nextNode;
            newNode.prev = prev;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        unlink(newNode);
        return newNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == object
                    || (current.value != null && current.value.equals(object))) {
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
        return head == null;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> previous = index < (size / 2) ? head : tail;
        if (previous == head) {
            for (int i = 0; i < index; i++) {
                previous = previous.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                previous = previous.prev;
            }
        }
        return previous;
    }

    private void unlink(Node<T> node) {
        if (head == tail) {
            head = tail = null;
        } else if (node.prev == null) {
            head = head.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }
}
