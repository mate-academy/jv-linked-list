package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int linkedListSize;

    public MyLinkedList() {
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode;
        if (linkedListSize == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
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
        Node<T> currentNode = findNode(index);
        Node<T> prevNode = currentNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, currentNode);
        if (prevNode != null) {
            prevNode.next = newNode;
        } else {
            head = newNode;
        }
        currentNode.prev = newNode;
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
        Node<T> findNode;
        if (index < linkedListSize / 2) {
            findNode = head;
            for (int i = 0; i < linkedListSize; i++) {
                if (i == index) {
                    return findNode;
                }
                findNode = findNode.next;
            }
        } else {
            findNode = tail;
            for (int i = linkedListSize - 1; i >= 0; i--) {
                if (i == index) {
                    return findNode;
                }
                findNode = findNode.prev;
            }
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
