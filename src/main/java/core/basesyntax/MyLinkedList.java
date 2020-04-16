
package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size = 0;

    @Override
    public boolean add(T value) {

        Node<T> tempLast = lastNode;
        lastNode = new Node<T>(value, null, tempLast);
        if (tempLast == null) {
            firstNode = lastNode;
        } else {
            tempLast.next = lastNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {

        if (index == size) {
            add(value);
        } else {
            Node<T> node = findByIndex(index);
            Node<T> prevNode = node.prev;
            Node<T> newNode = new Node<T>(value, node, prevNode);
            node.prev = newNode;
            if (prevNode == null) {
                firstNode = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
        }
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

        return findByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {

        Node<T> node = findByIndex(index);
        T t = node.element;
        node.element = value;
        return t;
    }

    @Override
    public T remove(int index) {

        Node<T> node = findByIndex(index);
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            firstNode = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            lastNode = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return node.element;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = firstNode;
        for (int i = 0; i < size; i++) {
            if ((t == null && t == node.element) || (t != null && t.equals(node.element))) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private Node<T> findByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node;
        if (index < size >> 1) {
            node = firstNode;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = lastNode;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
