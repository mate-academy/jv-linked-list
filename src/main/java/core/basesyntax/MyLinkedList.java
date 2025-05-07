package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
            tail = newNode;
            head.next = tail;
            tail.prev = head;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> previous = currentNode.prev;
        Node<T> newNode = new Node<>(previous, value, currentNode);
        previous.next = newNode;
        currentNode.prev = newNode;
        if (index == 0) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T previousItem = currentNode.item;
        currentNode.item = value;
        return previousItem;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = getNodeByObject(object);
        if (nodeToRemove != null) {
            unlink(nodeToRemove);
            return true;
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
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index %s is out of bounds. "
                    + "It must be within 0 to %s", index, size));
        }
    }

    private void checkGetIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %s is out of bounds. "
                    + "It must be within 0 to %s", index, size - 1));
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkGetIndex(index);
        if (index == size - 1) {
            return tail;
        }
        Node<T> node = head;
        if (size / 2 > index) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = 0; i < size - index - 1; i++) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous != null) {
            previous.next = next;
        }
        if (next != null) {
            next.prev = previous;
        }
        if (node == head) {
            head = next;
        }
        if (node == tail) {
            tail = previous;
        }
        size--;
        if (size == 0) {
            head = null;
            tail = null;
        }
        node = null;
    }

    private Node<T> getNodeByObject(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                return node;
            }
            node = node.next;
        }
        return null;
    }
}
