package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            Node<T> node = new Node<>(null, value, null);
            head = tail = node;
            size++;
            return;
        }

        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(null, value, null);

        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }

        Node<T> temp = head;

        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }

        newNode.next = temp.next;
        newNode.prev = temp;

        if (temp.next != null) {
            temp.next.prev = newNode;
        }

        temp.next = newNode;
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
        checkIndexOutOfBounds(index, size);

        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOutOfBounds(index, size);

        Node<T> temp = getNodeByIndex(index);
        T initialItem = temp.element;
        temp.element = value;

        return initialItem;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfBounds(index, size);

        Node<T> current = getNodeByIndex(index);
        unlink(current);
        return current.element;
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexByValue(object);

        if (index == -1) {
            return false;
        } else {
            unlink(getNodeByIndex(index));
        }
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

    private void checkIndexOutOfBounds(int index, int range) {
        if (index < 0 || index >= range) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;

        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }

        return node;
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

        size--;
    }

    private int findIndexByValue(T value) {
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            if (value == null && current.element == null) {
                return index;
            } else if (value != null && value.equals(current.element)) {
                return index;
            }

            current = current.next;
            index++;
        }

        return -1;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
