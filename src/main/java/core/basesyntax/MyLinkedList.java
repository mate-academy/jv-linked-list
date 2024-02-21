package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            addLast(value);
        } else {
            insertNodeAtIndex(value, index);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    private void insertNodeAtIndex(T value, int index) {
        Node<T> nodeAtIndex = getNode(index);
        Node<T> newNode = new Node<>(value, nodeAtIndex.prev, nodeAtIndex);
        if (nodeAtIndex.prev != null) {
            nodeAtIndex.prev.next = newNode;
        } else {
            first = newNode;
        }
        nodeAtIndex.prev = newNode;
        size++;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        return nodeToRemove.data;
    }

    @Override
    public boolean remove(T value) {
        Node<T> current = first;
        while (current != null) {
            if (value == null ? current.data == null : value.equals(current.data)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void addAll(List<T> elements) {
        for (T element : elements) {
            addLast(element);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        if (first == null) {
            first = last;
        }
        size++;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node == first) {
            first = node.next;
        }
        if (node == last) {
            last = node.prev;
        }
        size--;
    }
}
