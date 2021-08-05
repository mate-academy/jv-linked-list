package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node;
        node = new Node<>(tail, value, null);

        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }

        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
            size++;
            return;
        }

        Node<T> currentNode = getNode(index);
        Node<T> previous = currentNode.prev;
        Node<T> node = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev = node;
        previous.next = node;
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
        Node<T> currentNode = getNode(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T prevValue = currentNode.item;
        currentNode.item = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNode(index);
        unlink(currentNode);
        T oldValue = currentNode.item;
        currentNode.item = null;
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.item || object != null && object.equals(currentNode.item)) {
                unlink(currentNode);
                currentNode.item = null;
                size--;
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

    private void checkIndex(int someIndex) {
        if (someIndex < 0 || someIndex >= size) {
            throw new IndexOutOfBoundsException("Not appropriate index.");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        int half = size / 2;

        if (index <= half) {
            Node<T> node = head;
            int i = 0;
            while (i < index) {
                node = node.next;
                i++;
            }
            return node;
        }

        Node<T> node = tail;
        int i = size - 1;
        while (i > index) {
            node = node.prev;
            i--;
        }
        return node;
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
    }

}
