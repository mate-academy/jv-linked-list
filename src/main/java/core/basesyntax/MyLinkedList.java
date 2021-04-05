package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            addToHead(value);
            return true;
        }
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addToZeroIndex(value);
            return;
        }
        addToOtherIndex(value, index);
        return;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if ((size >> 1) < index) {
            return searchFromTail(index).data;
        }
        return searchFromHead(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldData = node.data;
        node.data = value;
        return oldData;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (index == 0) {
            Node<T> deletedHeadItem = head;
            unlinkHead();
            return deletedHeadItem.data;
        }

        if (index == size - 1) {
            Node<T> deletedTailItem = tail;
            unlinkTail();
            return deletedTailItem.data;
        }

        Node<T> deletedNode = getNode(index);
        unlinkNode(deletedNode);
        return deletedNode.data;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == get(i) || get(i).equals(object)) {
                remove(i);
                return true;
            }
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
        }
        return false;
    }

    private void addToOtherIndex(T value, int index) {
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<T>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    private void addToZeroIndex(T value) {
        Node<T> newNode = new Node<T>(null, value, head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void unlinkHead() {
        head = head.next;
        head.prev = null;
        size--;
    }

    private void unlinkTail() {
        tail = tail.prev;
        tail.next = null;
        size--;
    }

    private void unlinkNode(Node<T> deletedNode) {
        deletedNode.prev.next = deletedNode.next;
        deletedNode.next.prev = deletedNode.prev;
        size--;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if ((size >> 1) < index) {
            return searchFromTail(index);
        }
        return searchFromHead(index);
    }

    private Node<T> searchFromHead(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void addToHead(T value) {
        Node<T> node = new Node<>(null, value, null);
        head = node;
        tail = node;
        head.next = tail;
        tail.prev = head;
        size++;
    }

    private class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
