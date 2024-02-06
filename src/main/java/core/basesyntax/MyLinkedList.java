package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        isIndexOutForAddMethod(index);

        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = getNode(index - 1);
            newNode.next = current.next;
            current.next = newNode;
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

        Node<T> current = getNode(index);

        return current.value;
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

        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (Objects.equals(object, node.value)) {
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

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }

    private void isIndexOutForAddMethod(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current = head;
        while (current.next != null && index > 0) {
            current = current.next;
            index--;
        }
        return current;
    }

    private T unlink(Node<T> node) {
        if (node == null) {
            return null;
        }

        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next != null) {
            next.prev = prev;
        }

        node.prev = null;
        node.next = null;
        size--;
        return node.value;
    }
}
