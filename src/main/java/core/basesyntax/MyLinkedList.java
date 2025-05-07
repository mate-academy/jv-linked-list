package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> oldNode = getNode(index);
            Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T inputElement : list) {
            add(inputElement);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> thisNode = getNode(index);
        T oldValue = thisNode.value;
        thisNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        return terminateLink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> thisNode = head;

        while (thisNode != null) {
            if (thisNode.value == object
                        || thisNode.value != null && thisNode.value.equals(object)) {
                terminateLink(thisNode);
                return true;
            }
            thisNode = thisNode.next;
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> thisNode;
        if (index < size / 2) {
            thisNode = head;
            for (int i = 0; i < index; i++) {
                thisNode = thisNode.next;
            }
        } else {
            thisNode = tail;
            for (int i = size - 1; i > index; i--) {
                thisNode = thisNode.prev;
            }
        }
        return thisNode;
    }

    private T terminateLink(Node<T> thisNode) {
        Node<T> previousLink = thisNode.prev;
        Node<T> nextLink = thisNode.next;

        if (previousLink == null) {
            head = nextLink;
        } else if (nextLink == null) {
            tail = previousLink;
        } else {
            previousLink.next = nextLink;
            nextLink.prev = previousLink;
        }
        size--;
        return thisNode.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

}
