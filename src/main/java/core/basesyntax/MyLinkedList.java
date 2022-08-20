package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            this.head = newNode;
        } else {
            tail.next = newNode;
        }
        this.tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index,size + 1);
        if (index == size) {
            add(value);
        } else {
            Node<T> node = findValueByIndex(index);
            Node<T> newNode;
            if (node == head) {
                newNode = new Node<>(null, value, node);
                head = newNode;
            } else {
                newNode = new Node<>(node.prev, value, node);
                node.prev.next = newNode;
            }
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return findValueByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        if (index == size) {
            add(value);
        }
        checkIndex(index, size);
        Node<T> node = findValueByIndex(index);
        Node<T> newNode;
        if (size - 1 == index) {
            newNode = new Node<>(node.prev, value, null);
            node.prev.next = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode = new Node<>(null, value, node.next);
            node.next.prev = newNode;
            head = newNode;
        } else {
            newNode = new Node<>(node.prev, value, node.next);
            node.next.prev = newNode;
            node.prev.next = newNode;
        }
        return node.value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> node = findValueByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value != null && node.value.equals(object) || node.value == object) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        size--;
        if (node.prev == null) {
            node.next.prev = null;
            this.head = head.next;
            return;
        } else if (node.next == null) {
            node.prev.next = null;
            this.tail = tail.prev;
            return;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;

        }
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " was not found!");
        }
    }

    private Node<T> findValueByIndex(int index) {
        Node<T> values;
        if (index < size / 2) {
            values = head;
            for (int i = 0; i < index; i++) {
                values = values.next;
            }
        } else {
            values = tail;
            for (int i = size - 1; i > index; i--) {
                values = values.prev;
            }
        }
        return values;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
