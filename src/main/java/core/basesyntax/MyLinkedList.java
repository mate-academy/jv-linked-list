package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> node;
    private Node<T> header;

    public MyLinkedList() {
        header = new Node<T>(null, null, null);
        size = 0;
    }

    public MyLinkedList(T value) {
        node = new Node<T>(value, null, null);
        header = new Node<T>(null, node, node);
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            node = new Node<T>(value, null, null);
            header.next = node;
        } else {
            node = new Node<T>(value, node, null);
            node.prev.next = node;
        }
        header.prev = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> currentNode;
        if (index != 0 && index != size) {
            currentNode = nodeIndexOf(index);
            currentNode.prev.next = new Node<T>(value, currentNode.prev, currentNode);
            currentNode.prev = currentNode;
        }
        if (index == 0 && size != 0) {
            header.prev = new Node<T>(value, null, header.next);
        } else {
            add(value);
            size--;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return nodeIndexOf(index).item;
    }

    @Override
    public T set(T value, int index) {
        T previouslyValue;
        Node<T> currentNode = nodeIndexOf(index);
        previouslyValue = currentNode.item;
        currentNode.item = value;
        return previouslyValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeIndexOf = nodeIndexOf(index);
        if (index != 0) {
            nodeIndexOf.prev.next = nodeIndexOf.next;
        } else {
            header.next = nodeIndexOf.next;
        }
        if (index != size - 1) {
            nodeIndexOf.next.prev = nodeIndexOf.prev;
        }
        nodeIndexOf.next = nodeIndexOf.prev = null;
        T item = nodeIndexOf.item;
        nodeIndexOf.item = null;
        size--;
        return item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = header.next;
        for (int i = 0; i < size; i++) {
            if (t == currentNode.item || t != null && t.equals(currentNode.item)) {
                remove(i);
                return true;
            } else {
                currentNode = currentNode.next;
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
        return size == 0;
    }

    private void indexOutOfBoundCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> nodeIndexOf(int index) {
        indexOutOfBoundCheck(index);
        Node<T> currentNode = header;
        if (index < (size << 1)) {
            for (int i = 0; i <= index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
