package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION = "Wrong index - ";
    private Node first;
    private Node last;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION + index);
        }

        if (index == size) {
            add(value);
        } else {
            Node newNode = new Node(value);
            Node current = getNode(index);

            newNode.prev = current.prev;
            newNode.next = current;
            current.prev = newNode;

            if (newNode.prev != null) {
                newNode.prev.next = newNode;
            } else {
                first = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION + index);
        }
        return getNode(index).data;
    }

    public Node getNode(int index) {
        Node value = first;
        for (int i = 0; i < index; i++) {
            value = value.next;
        }
        return value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION + index);
        }
        Node current = getNode(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION + index);
        }
        Node value = getNode(index);

        if (value.prev != null) {
            value.prev.next = value.next;
        } else {
            first = value.next;
        }

        if (value.next != null) {
            value.next.prev = value.prev;
        } else {
            last = value.prev;
        }

        size--;
        return value.data;
    }

    @Override
    public boolean remove(T object) {
        Node value = first;
        while (value != null) {
            if (object == null) {
                if (value.data == null) {
                    removeNode(value);
                    return true;
                }
            } else if (object.equals(value.data)) {
                removeNode(value);
                return true;
            }
            value = value.next;
        }
        return false;
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private T data;
        private Node prev;
        private Node next;

        Node(T data) {
            this.data = data;
        }
    }
}
