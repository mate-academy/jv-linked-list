package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            Node<T> node = getNodeIndex(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T e : list) {
            add(e);
        }
    }

    @Override
    public T get(int index) {
        return getNodeIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = getNodeIndex(index);
        T item = oldNode.item;
        oldNode.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (node != null) {
            if (isEquals(object, node.item)) {
                unlink(node);
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
        return (first == null);
    }

    private boolean isEquals(T object, T node) {
        return object == node || object != null && object.equals(node);
    }

    private Node<T> getNodeIndex(int index) {
        isValidIndex(index);
        Node<T> node;
        int pointerIndex;
        if (index < size / 2) {
            node = first;
            pointerIndex = 0;
            while (pointerIndex < index) {
                node = node.next;
                pointerIndex++;
            }
        } else {
            node = last;
            pointerIndex = size - 1;
            while (pointerIndex > index) {
                node = node.prev;
                pointerIndex--;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = null;
            last = null;
        } else if (node == first) {
            first = node.next;
            first.prev = null;
        } else if (node == last) {
            last = node.prev;
            last.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void isValidIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
