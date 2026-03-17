package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private transient int size = 0;
    private transient Node<T> head;
    private transient Node<T> tail;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) throws IndexOutOfBoundsException {
        isValidAddIndex(index);

        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> retrievedNode = node(index);
        T oldValue = retrievedNode.item;
        retrievedNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isValidAccessIndex(index);
        Node<T> targetNode = node(index);
        T item = node(index).item;
        unlinkNode(targetNode);

        return item;

    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;

        while (currentNode != null) {
            if (currentNode.item == null && object == null) {
                unlinkNode(currentNode);
                return true;
            }
            if (currentNode.item != null && currentNode.item.equals(object)) {
                unlinkNode(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void linkLast(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> successor) {
        final Node<T> previous = successor.prev;
        final Node<T> newNode = new Node<>(previous, value, successor);
        successor.prev = newNode;

        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private void isValidAccessIndex(int index) throws IndexOutOfBoundsException {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void isValidAddIndex(int index) throws IndexOutOfBoundsException {
        if (!(0 <= index && index <= size)) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> node(int index) throws IndexOutOfBoundsException {
        isValidAccessIndex(index);
        int counter = 0;

        if (index < size / 2) {
            Node<T> resultNode = head;
            while (counter != index) {
                resultNode = resultNode.next;
                counter++;
            }
            return resultNode;

        } else {
            Node<T> resultNode = tail;
            counter = size - 1;
            while (counter != index) {
                resultNode = resultNode.prev;
                counter--;
            }
            return resultNode;
        }
    }

    private void unlinkNode(Node<T> currentNode) {
        if (currentNode.prev == null) {
            head = currentNode.next;
        }
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode.next == null) {
            tail = currentNode.prev;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        currentNode.item = null;
        currentNode.prev = null;
        currentNode.next = null;
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
