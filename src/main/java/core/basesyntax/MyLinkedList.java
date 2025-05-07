package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (size > 0) {
            newNode = new Node<>(value, lastNode, null);
            lastNode.next = newNode;
            lastNode = newNode;
            size++;
            return;
        }
        newNode = new Node<>(value, null, null);
        firstNode = newNode;
        lastNode = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        putNodeBefore(value, getNodeByIndex(index));
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new NoSuchElementException("You can't add any data from empty list");
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T element = node.element;
        node.element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.element;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            throw new NoSuchElementException("This list is empty! "
                    + "You can't remove anything from it!");
        }
        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            if (object == currentNode.element
                    || (object != null && object.equals(currentNode.element))) {
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

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index [" + index
                    + "] is out of available range. Available range is [0 - " + (size - 1) + "]");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index > size >> 1) {
            node = lastNode;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
        node = firstNode;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void putNodeBefore(T value, Node<T> node) {
        Node<T> newNode;
        if (node == firstNode) {
            newNode = new Node<>(value, null, firstNode);
            firstNode.prev = newNode;
            firstNode = newNode;
            size++;
            return;
        }
        newNode = new Node<>(value, node.prev, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    private void unlink(Node<T> node) {
        if (node != firstNode && node != lastNode) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        if (node == firstNode && size > 1) {
            node.next.prev = null;
            firstNode = node.next;
        }
        if (node == lastNode && size > 1) {
            node.prev.next = null;
            lastNode = node.prev;
        }
        if (firstNode == lastNode) {
            firstNode = null;
            lastNode = null;
        }
        size--;
    }

    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
