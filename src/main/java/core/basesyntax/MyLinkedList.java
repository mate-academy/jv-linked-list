package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    {
        size = 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            Node node = new Node<>(null, value, null);
            head = node;
            tail = head;
        } else {
            Node node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexIfAdd(index);
        if (size == 0 && index == 0) {
            Node node = new Node(null, value, null);
            tail = node;
            head = node;
        } else if (size > 0 && index == 0) {
            Node node = new Node(null, value, head);
            head.prev = node;
            head = node;
        } else if (size > 0 && index == size) {
            Node node = new Node(tail, value, null);
            tail.next = node;
            tail = node;
        } else {
            Node currentNode = indexIterator(index);
            Node node = new Node(currentNode.prev, value, currentNode);
            currentNode.prev.next = node;
            currentNode.prev = node;
        }
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
        checkIndex(index);
        return (T) indexIterator(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node currentNode = indexIterator(index);
        T tempValue = (T) currentNode.item;
        currentNode.item = value;
        return tempValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (size == 1) {
            tail = null;
            Node currentNode = head;
            head = null;
            size = 0;
            return (T) currentNode.item;
        }
        if (size > 1 && index == 0) {
            Node currentNode = head;
            unlinkNodeFromHead(currentNode);
            return (T) currentNode.item;
        } else if (size > 1 && index == size - 1) {
            Node currentNode = tail;
            unlinkNodeFromTail(currentNode);
            return (T) currentNode.item;
        }
        Node currentNode = indexIterator(index);
        unlinkNode(currentNode);
        return (T) currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        if (size == 1 && equalityCheck(currentNode, object)) {
            tail = null;
            head = null;
            size = 0;
            return true;
        }
        if (size > 1 && equalityCheck(currentNode, object)) {
            unlinkNodeFromHead(currentNode);
            return true;
        }
        while (currentNode != null) {
            if (currentNode.next != null && equalityCheck(currentNode, object)) {
                unlinkNode(currentNode);
                return true;
            } else if (currentNode == null && equalityCheck(currentNode, object)) {
                unlinkNodeFromTail(currentNode);
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
        return (size == 0);
    }

    private Node indexIterator(int index) {
        if (index < size / 2) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void checkIndexIfAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void unlinkNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private void unlinkNodeFromHead(Node node) {
        node.next.prev = null;
        head = node.next;
        size--;
    }

    private void unlinkNodeFromTail(Node node) {
        node.prev.next = null;
        tail = node.prev;
        size--;
    }

    private boolean equalityCheck(Node node, T object) {
        return (node.item == object
                || object != null && node.item.equals(object));
    }
}
