package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node first;
    private Node last;

    private Node getFirst() {
        return first;
    }

    private void setFirst(Node first) {
        this.first = first;
    }

    private Node getLast() {
        return last;
    }

    private void setLast(Node last) {
        this.last = last;
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            linkLast(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node currentNode = node(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node current = node(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = getFirst();
        while (currentNode != null) {
            if ((object == currentNode.item)
                    || (object != null && object.equals(currentNode.item))) {
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

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        Node(Node prev, T item, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node node(int index) {
        Node currentNode;
        if (index < (size / 2)) {
            currentNode = getFirst();
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            currentNode = getLast();
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private void linkLast(T t) {
        Node prevNode = getLast();
        Node newNode = new Node(prevNode, t, null);
        setLast(newNode);

        if (prevNode == null) {
            setFirst(newNode);
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T t, Node node) {
        Node prevNode = node.prev;
        Node newNode = new Node(prevNode, t, node);
        node.prev = newNode;

        if (prevNode == null) {
            setFirst(newNode);
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private void unlink(Node node) {
        if (node.prev == null) {
            setFirst(node.next);
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            setLast(node.prev);
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
    }
}
