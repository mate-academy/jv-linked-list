package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> nodeHead;
    private Node<T> nodeTail;
    private int size;

    public MyLinkedList() {
        this.nodeHead = this.nodeTail = new Node(null, null, null);
        this.size = 0;
    }

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element, Node<T> prev, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> currentNode = nodeHead;
        for (int i = 0; i < size; i++) {
            currentNode = currentNode.next;
        }
        Node<T> newNode = new Node<T>(value, currentNode, null);
        if (nodeHead == null) {
            nodeHead = newNode;
            nodeTail = newNode;
        } else {
            newNode.prev = nodeTail;
            nodeTail.next = newNode;
            nodeTail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("No such index " + index);
        }
        Node<T> currentNode = nodeHead;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        Node<T> newNode = new Node<>(value, currentNode, currentNode.next);
        if (currentNode.next != null) {
            currentNode.next.prev = newNode;
        }
        currentNode.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index " + index);
        }
        Node<T> currentNode = nodeHead;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index " + index);
        }
        Node<T> currentNode = nodeHead;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }

        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index " + index);
        }
        Node<T> currentNode = nodeHead;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }

        if (currentNode.prev == null) {
            nodeHead = currentNode.next;
            size--;
        } else if (currentNode.next == null) {
            nodeTail = currentNode.prev;
            size--;
        } else {
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
            size--;
        }
        return currentNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = nodeHead;
        for (int i = 0; i < size; i++) {
            node = node.next;
            if (node.element == object || (node.element != null && node.element.equals(object))) {
                if (node.prev == null && node.next == null) {
                    nodeHead = nodeTail = null;
                } else if (node.prev == null) {
                    nodeHead = node.next;
                } else if (node.next == null) {
                    nodeTail = node.prev;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
                size--;
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
        return size == 0 ? true : false;
    }
}
