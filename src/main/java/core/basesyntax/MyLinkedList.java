package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> tailNew = new Node<>(null, value, null);
        if (isEmpty()) {
            tail = tailNew;
            head = tail;
        } else if (size >= 1) {
            tailNew.prev = tail;
            tail.next = tailNew;
            tail = tailNew;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range");
        } else if (size <= 1 || index == size) {
            add(value);
            return;
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
        } else {
            Node<T> current = getNodeByIndex(index);
            Node<T> nodeNew = new Node<>(current.prev, value, current);
            nodeNew.prev.next = nodeNew;
            current.prev = nodeNew;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T entry : list) {
            add(entry);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T valueToReturn = current.value;
        current.value = value;
        return valueToReturn;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNodeByIndex(index);
        T valueToReturn = current.value;
        unlink(current);
        return valueToReturn;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object || (current.value != null
                    && current.value.equals(object))) {
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

    private Node<T> getNodeByIndex(int index) {
        ifIndexOutOfBounds(index);
        Node<T> current = index <= size / 2 ? head : tail;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void ifIndexOutOfBounds(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range");
        }
    }

    private void unlink(Node<T> current) {
        if (current.equals(head) && head == tail) {
            head = tail = null;
        } else if (current.equals(head)) {
            head = head.next;
            head.prev = null;
        } else if (current.equals(tail)) {
            tail = tail.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }
}
