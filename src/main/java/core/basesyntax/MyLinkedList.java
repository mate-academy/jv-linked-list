package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {}

    @Override
    public boolean add(T value) {
        add(value, size);
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index == size) {
            linkLast(value);
        } else if (index == 0) {
            linkFirst(value);
        } else {
            indexValidator(index);
            Node<T> currentNode = getNode(index);
            newNode = new Node<>(currentNode.previous, value, currentNode);
            currentNode.previous.next = newNode;
            currentNode.previous = newNode;
        }
        ++size;
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
        indexValidator(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexValidator(index);
        Node<T> node = getNode(index);
        T returnValue = node.value;
        node.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        indexValidator(index);
        Node<T> nodeAtIndex = getNode(index);
        unlinkNode(nodeAtIndex);
        return nodeAtIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((object == null && currentNode.value == null)
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                unlinkNode(currentNode);
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
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size; i > index + 1; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private void linkFirst(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.previous = newNode;
        head = newNode;
    }

    private void linkLast(T value) {
        if (size == 0) {
            head = tail = new Node<>(null, value, null);
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
    }

    private void unlinkNode(Node<T> node) {
        if (node == tail) {
            tail.next = null;
            tail = node.previous;
        } else if (node == head) {
            head = node.next;
            head.previous = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        --size;
    }

    private void indexValidator(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private static class Node<E> {
        E value;
        Node<E> previous;
        Node<E> next;

        public Node(Node<E> previous, E value, Node<E> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
