package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(value);
            tail = head;
            size = 1;
        } else {
            tail.next = new Node<>(value);
            tail.next.prev = tail;
            tail = tail.next;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> oldNode = findByIndex(index);
            if (oldNode == null) {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
                size++;
            } else {
                addToMiddle(value, oldNode);
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elem : list) {
            add(elem);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = findByIndex(index);
        T old = current.value;
        current.value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        Node<T> oldNode = findByIndex(index);
        unlink(oldNode);
        return oldNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> oldNode = findByElement(object);
        if (oldNode == null) {
            return false;
        }
        unlink(oldNode);
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

    private Node<T> findByIndex(int index) {
        checkIndexForGet(index);
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

    private void addToMiddle(T value, Node<T> prevNode) {
        Node<T> newNode = new Node<>(value, prevNode, prevNode.prev);
        prevNode.prev.next = newNode;
        prevNode.prev = newNode;
        size++;
    }

    private void checkIndexForGet(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("invalid index");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("invalid index");
        }
    }

    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }
        if (node == head) {
            head = head.next;
        } else if (node == tail) {
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> findByElement(T element) {
        Node<T> current = head;
        while (current != null) {
            if ((current.value != null && current.value.equals(element))
                    || current.value == element) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value) {
            this.value = value;
        }

        private Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
