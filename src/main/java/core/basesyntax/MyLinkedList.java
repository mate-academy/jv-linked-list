package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(tail, value, null);

        if (isEmpty()) {
            head = addedNode;
        } else {
            tail.next = addedNode;
        }

        tail = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            head = new Node<>(null, value, head);
            head.next.prev = head;
        } else {
            Node<T> nodeAtIndex = getNode(index);
            Node<T> newNode = new Node<>(nodeAtIndex.prev, value, nodeAtIndex);
            nodeAtIndex.prev.next = newNode;
            nodeAtIndex.prev = newNode;
        }

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

        return currentNode != null ? currentNode.item : null;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T itemToReturn = currentNode.item;
        currentNode.item = value;
        return itemToReturn;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;

        for (int i = 0; i < size; i++) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }

        if (index == 0) {
            return head;
        } else if (index == size - 1) {
            return tail;
        }

        int start = (index <= size / 2) ? 0 : size - 1;
        int step = (start == 0) ? 1 : -1;
        Node<T> currentNode = (start == 0) ? head : tail;

        for (int i = start; i != index; i += step) {
            currentNode = (step == 1) ? currentNode.next : currentNode.prev;
        }

        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = tail = null;
        } else if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }

        size--;
    }

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
