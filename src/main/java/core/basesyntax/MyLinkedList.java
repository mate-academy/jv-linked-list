package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int linkedListSize;

    public MyLinkedList() {
        linkedListSize = 0;
    }

    @Override
    public boolean add(T value) {
        if (linkedListSize == 0) {
            Node<T> firstNode = new Node<>(null, value, null);
            head = firstNode;
            tail = firstNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        linkedListSize++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == linkedListSize) {
            add(value);
            return;
        }
        Node<T> findNode = findNode(index);
        Node<T> prevNode = findNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, findNode);
        if (prevNode != null) {
            prevNode.next = newNode;
        } else {
            head = newNode;
        }
        findNode.prev = newNode;
        linkedListSize++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> findNode = findNode(index);
        T oldValue = findNode.item;
        findNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> findNode = findNode(index);
        delete(findNode);
        return findNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> findNode = head;
        for (int i = 0; i < linkedListSize; i++) {
            if (findNode.item == object || findNode.item != null
                    && findNode.item.equals(object)) {
                return delete(findNode);
            }
            findNode = findNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return linkedListSize;
    }

    @Override
    public boolean isEmpty() {
        return linkedListSize == 0;
    }

    private boolean delete(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }
        linkedListSize--;
        return true;
    }

    private Node<T> findNode(int index) {
        Node<T> findNode = head;
        for (int i = 0; i < linkedListSize; i++) {
            if (i == index) {
                return findNode;
            }
            findNode = findNode.next;
        }
        throw new IndexOutOfBoundsException("Index [" + index + "] does not exist!");
    }

    static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
