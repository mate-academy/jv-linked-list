package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> current = new Node<>(tail, value, null);
            tail.next = current;
            tail = current;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        if (size == 0 || index == size) {
            add(value);
        } else if (index == 0) {
            Node current = new Node<>(null, value, head);
            head.prev = current;
            head = current;
            size++;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            Node<T> next = prev.next;
            Node<T> current = new Node<>(prev, value, next);
            prev.next = current;
            next.prev = current;
            size++;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T nodeData : list) {
            add(nodeData);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeByIndex(index);
        T removedValue = nodeToRemove.data;
        unlink(nodeToRemove);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            return false;
        }
        Node<T> current = head;
        while (current.data == null ? object != null : !current.data.equals(object)) {
            if (current.next == null) {
                return false;
            }
            current = current.next;
        }
        unlink(current);
        return true;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        Node<T> current;
        if (index < size / 2) {
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

    private void unlink(Node<T> nodeToRemove) {
        if (nodeToRemove == head) {
            head = nodeToRemove.next;
            if (head != null) {
                head.prev = null;
            }
        } else if (nodeToRemove == tail) {
            tail = nodeToRemove.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            Node<T> prev = nodeToRemove.prev;
            Node<T> next = nodeToRemove.next;
            prev.next = next;
            next.prev = prev;
        }
        size--;
    }

    private class Node<T> {
        private Node<T> prev;
        private T data;
        private Node<T> next;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
