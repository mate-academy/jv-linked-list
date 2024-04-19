package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        Node<T> newNode = new Node<>(null, null, value);
        if (isEmpty()) {
            linkHead(newNode);
        } else if (index == 0) {
            insertAtHead(newNode);
        } else if (index == size) {
            linkTail(newNode);
        } else {
            Node<T> current = getNode(index);
            insertAmong(newNode, current);
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
        Node<T> node = getNode(index);
        if (node != null) {
            return node.value;
        } else {
            throw new IndexOutOfBoundsException("Index is not found! " + index);
        }
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNode(index);
        T previousValue = newNode.value;
        newNode.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = getNode(index);
        unlink(removeNode);
        return removeNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = head;
        while (removeNode != null) {
            if (object == null ? removeNode.value == null : object.equals(removeNode.value)) {
                unlink(removeNode);
                return true;
            }
            removeNode = removeNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index not found!" + index);
        }
    }

    private void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index not found!" + index);
        }
    }

    private void linkHead(Node<T> newNode) {
        head = newNode;
        tail = newNode;
    }

    private void insertAtHead(Node<T> newNode) {
        newNode.next = head;
        head.previous = newNode;
        head = newNode;
    }

    private void linkTail(Node<T> newNode) {
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
        }
        tail = newNode;
    }

    private void insertAmong(Node<T> newNode, Node<T> current) {
        newNode.next = current;
        if (current != null) {
            newNode.previous = current.previous;
            if (current.previous != null) {
                current.previous.next = newNode;
            }
            current.previous = newNode;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = head;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private static class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private T value;

        Node(Node<T> previous, Node<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
    }

    private void unlink(Node<T> node) {
        if (node != null) {
            if (node.previous != null) {
                node.previous.next = node.next;
            }

            if (node.next != null) {
                node.next.previous = node.previous;
            }

            if (tail == node) {
                tail = node.previous;
            }

            if (head == node) {
                head = node.next;
            }
            size--;
        }
    }
}
