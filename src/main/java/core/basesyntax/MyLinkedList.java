package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLast(value);
        } else {
            Node<T> currentNode = getNode(index);
            addBefore(value, currentNode);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndexExistence(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexExistence(index);
        Node<T> currentNode = getNode(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndexExistence(index);
        Node<T> currentNode = getNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object || object != null && object.equals(currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        Node<T> current;
        current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        }
        if (size == 0) {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    private void addBefore(T value, Node<T> target) {
        Node<T> newNode = new Node<>(target.prev, value, target);
        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        }
        if (head == target) {
            head = newNode;
        }
        target.prev = newNode;
        size++;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (head == node) {
            head = node.next;
        }
        if (tail == node) {
            tail = node.prev;
        }
        size--;
    }

    private void checkIndexExistence(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
