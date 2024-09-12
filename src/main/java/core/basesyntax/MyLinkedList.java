package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        linkNodes(tail, newNode);
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
            size++;
            return;
        }

        Node<T> current = getNodeAt(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        linkNodes(current.prev, newNode);
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeAt(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeAt(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNodeAt(index);
        T removedValue = nodeToRemove.value;
        unlink(nodeToRemove);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        // Handle null object parameter explicitly
        Node<T> current = head;
        while (current != null) {
            if (object == null ? current.value == null : object.equals(current.value)) {
                unlink(current);
                size--;
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

    private Node<T> getNodeAt(int index) {
        if (index < size / 2) {
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
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private void linkNodes(Node<T> prevNode, Node<T> nextNode) {
        if (prevNode != null) {
            prevNode.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        }
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
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

    }
}
