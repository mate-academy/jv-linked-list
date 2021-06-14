package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private Node<T> currentNode;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value, null);
            size++;
            tail = head;
            return;
        }
        currentNode = tail;
        currentNode.next = new Node<>(currentNode, value, null);
        tail = currentNode.next;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addFirst(value, head);
            size++;
            return;
        }
        currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        checkExistingNode(index);
        currentNode = getNodeByIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkExistingNode(index);
        currentNode = getNodeByIndex(index);
        T oldItem = currentNode.item;
        currentNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkExistingNode(index);
        currentNode = getNodeByIndex(index);
        unlink(currentNode);
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object || currentNode.item.equals(object)) {
                unlink(currentNode);
                size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private void unlink(Node<T> currentNode) {
        if (isHead(currentNode)) {
            removeHead(currentNode);
            return;
        }
        if (isLast(currentNode)) {
            removeTail(currentNode);
            return;
        }
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void removeTail(Node<T> currentNode) {
        currentNode.prev.next = null;
    }

    private void removeHead(Node<T> currentNode) {
        head = currentNode.next;
    }

    private void addFirst(T value, Node<T> oldFirst) {
        Node<T> newNode = new Node<>(null, value, oldFirst);
        if (oldFirst != null) {
            oldFirst.prev = newNode;
        }
        head = newNode;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        int indexCurrentNode = 0;
        while (currentNode != null && indexCurrentNode != index) {
            currentNode = currentNode.next;
            indexCurrentNode++;
        }

        return currentNode;
    }

    private boolean isHead(Node<T> node) {
        return node == head;
    }

    private boolean isLast(Node<T> node) {
        return node == tail;
    }

    private void checkExistingNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
