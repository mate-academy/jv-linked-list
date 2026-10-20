package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int counter;

    public MyLinkedList() {
        head = null;
        tail = null;
        counter = 0;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = tail = new Node(value, null, null);
        } else {
            Node newNode = new Node(value, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        counter++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > counter) {
            throw new IndexOutOfBoundsException(
                    "passed index out of bounds of the list.");
        }
        if (index == counter) {
            add(value);
        } else if (index == 0) {
            Node newNode = new Node(value, null, head);
            head.prev = newNode;
            head = newNode;
            counter++;
        } else {
            Node currentNode = getNode(index);
            Node newNode = new Node(value, currentNode.prev, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            counter++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        Node currentNode = getNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node nodeForSet = getNode(index);
        T removedValue = nodeForSet.value;
        nodeForSet.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node nodeForRemove = getNode(index);
        unlink(nodeForRemove);
        return nodeForRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;

        while (currentNode != null) {
            if (java.util.Objects.equals(currentNode.value, object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    private void unlink(Node node) {
        if (node.equals(head)) {
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else if (node.equals(tail)) {
            tail = tail.prev;
            if (tail == null) {
                head = null;
            } else {
                tail.next = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        counter--;
    }

    private Node getNode(int index) {
        Node currentNode = head;
        int currentIndex = 0;

        while (currentIndex != index) {
            currentNode = currentNode.next;
            currentIndex++;
        }
        return currentNode;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= counter) {
            throw new IndexOutOfBoundsException(
                    "passed index out of bounds of the list.");
        }
    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        private Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
