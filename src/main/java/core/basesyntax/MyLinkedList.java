package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index has not been found");
        }

        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = newNode;
            nodeByIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T e : list) {
            add(e);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        }

        if (head == null) {
            return null;
        } else {
            Node<T> newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode.value;
        }
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index has not been found");
        }

        if (index == 0) {
            T headValue = head.value;
            Node<T> newNode = new Node<>(null, value, head.next);
            if (head.next != null) {
                head.next.prev = newNode;
            }
            head = newNode;
            return headValue;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex.next);
            nodeByIndex.prev.next = newNode;
            nodeByIndex.next.prev = newNode;
            return nodeByIndex.value;
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            unlink(nodeByIndex);
            return nodeByIndex.value;
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = head;

        for (int i = 0; i < size; i++) {
            if (object == newNode.value || object != null && object.equals(newNode.value)) {
                unlink(newNode);
                return true;
            }
            newNode = newNode.next;
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
        private Node<T> prev;
        private Node<T> next;
        private final T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;

        if (size == 1) {
            tail = head = null;
        } else if (node.next == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else if (node.prev == null) {
            head.next.prev = null;
            head = head.next;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }

        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;

        if (index > size / 2) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }

        return currentNode;
    }
}
