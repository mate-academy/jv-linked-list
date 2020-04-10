package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            last = new Node<T>(value, null, null);
            first = last;
        } else {
            last = new Node<T>(value, last, null);
            last.prev.next = last;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> nodeIndexOf;
        if (index == size) {
            add(value);
        } else if (index == 0) {
            first = new Node<T>(value, null, first);
            size++;
        } else {
            nodeIndexOf = nodeIndexOf(index);
            nodeIndexOf.prev.next = new Node<T>(value, nodeIndexOf.prev, nodeIndexOf);
            nodeIndexOf.prev = nodeIndexOf.prev.next;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
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
        Node<T> nodeIndexOf = nodeIndexOf(index);
        previouslyValue = nodeIndexOf.item;
        nodeIndexOf.item = value;
        return previouslyValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeIndexOf = nodeIndexOf(index);
        if (index != 0) {
            nodeIndexOf.prev.next = nodeIndexOf.next;
        } else {
            first = nodeIndexOf.next;
        }
        if (index != size - 1) {
            nodeIndexOf.next.prev = nodeIndexOf.prev;
        } else {
            last = nodeIndexOf.prev;
        }
        nodeIndexOf.prev = nodeIndexOf.next = null;
        T item = nodeIndexOf.item;
        nodeIndexOf.item = null;
        size--;
        return item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = first;
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
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
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
