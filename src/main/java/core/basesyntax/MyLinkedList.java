package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> currentNode;
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
        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            addFirst(value, head);
            size++;
            return;
        }

        checkExistingNode(index);

        Node<T> currentNode = getNodeByIndexFromHead(index);
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
        Node<T> currentNode;
        checkExistingNode(index);
        currentNode = getNodeByIndexFromHead(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkExistingNode(index);

        Node<T> currentNode = getNodeByIndexFromHead(index);
        T oldItem = currentNode.item;
        currentNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkExistingNode(index);

        Node<T> currentNode = getNodeByIndexFromHead(index);
        unlink(currentNode);
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> node) {
        if (isHead(node)) {
            removeHead(node);
            return;
        }
        if (isLast(node)) {
            removeTail(node);
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void removeTail(Node<T> node) {
        if (node.prev == null) {
            head = tail = null;
            return;
        }
        tail = node.prev;
        tail.next = null;
    }

    private void removeHead(Node<T> node) {
        if (node.next == null) {
            head = tail = null;
            return;
        }
        head = node.next;
        head.prev = null;
    }

    private void addFirst(T value, Node<T> head) {
        Node<T> newNode = new Node<>(null, value, head);
        if (head != null) {
            head.prev = newNode;
        }
        this.head = newNode;
    }

    private Node<T> getNodeByIndexFromHead(int index) {
        if (index > size / 2) {
            return getNodeByIndexFromTail(index);
        }
        int indexCurrentNode = 0;
        Node<T> currentNode = head;
        while (currentNode != null && indexCurrentNode != index) {
            currentNode = currentNode.next;
            indexCurrentNode++;
        }

        return currentNode;
    }

    private Node<T> getNodeByIndexFromTail(int index) {
        Node<T> currentNode = tail;
        int indexCurrentNode = size - 1;
        while (currentNode != null && indexCurrentNode != index) {
            currentNode = currentNode.prev;
            indexCurrentNode--;
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
