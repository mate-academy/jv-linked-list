package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        size++;
        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    @Override
    public void add(T value, int index) {
        Node<T> current = head;
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException(
                    "Index " + index + " is out bounds. List size is " + size);
        }
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        if (current == null) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        Node<T> prev = current.prev;
        newNode.prev = prev;
        newNode.next = current;
        current.prev = newNode;
        if (prev != null) {
            prev.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T val: list) {
            add(val);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if ((current.value == object)
                    || (current.value != null && current.value.equals(object))) {
                break;
            }
            current = current.next;
        }
        if (current != null) {
            unlink(current);
            return true;
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

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(
                    "Index " + index + " is out bounds. List size is " + size);
        }
        Node<T> current;
        if (index >= size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }
        node.prev = null;
        node.next = null;
        size--;
    }

    private static class Node<V> {
        private V value;
        private Node<V> prev;
        private Node<V> next;

        public Node(V value) {
            this.value = value;
        }
    }
}
