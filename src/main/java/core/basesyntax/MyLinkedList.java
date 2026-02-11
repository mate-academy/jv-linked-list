package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    //PUBLIC METHODS

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, null);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value, null, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
            size++;
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(value, current.prev, current);
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> items) {
        if (items != null && !items.isEmpty()) {
            for (T item : items) {
                add(item);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T value) {
        Node<T> current = head;
        while (current != null) {
            if ((value == null && current.value == null) || (value != null && value.equals(current.value))) {
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
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    //PRIVATE METHODS

    private Node<T> getNode(int index) {
        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private T unlink(Node<T> node) {
        T value = node.value;

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
        return value;
    }

    //NODE CLASS
    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
