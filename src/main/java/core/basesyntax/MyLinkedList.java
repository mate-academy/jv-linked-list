package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node head;
    private Node tail;

    public MyLinkedList() {
        tail = null;
        head = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        if (index == size) {
            add(value);
        } else {
            Node newNode = new Node(value);
            Node currentNode = getNodeAtIndex(index);
            Node previousNode = currentNode.prev;

            newNode.next = currentNode;
            newNode.prev = previousNode;

            if (previousNode == null) {
                head = newNode;
            } else {
                previousNode.next = newNode;
            }

            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        return getNodeAtIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node node = getNodeAtIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        Node nodeToRemove = getNodeAtIndex(index);
        T valueToRemove = nodeToRemove.item;
        unlink(nodeToRemove);
        size--;
        return valueToRemove;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        while (currentNode != null) {
            if ((currentNode.item == object)
                    || (currentNode.item != null && currentNode.item.equals(object))) {
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

    private Node getNodeAtIndex(int index) {
        if (0 > index || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current;
    }

    private void unlink(Node node) {
        if (node == head) {
            head = head.next;
        } else {
            node.prev.next = node.next;
        }

        if (node == tail) {
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
        }
    }

    class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T value) {
            item = value;
            prev = null;
            next = null;
        }
    }
}
