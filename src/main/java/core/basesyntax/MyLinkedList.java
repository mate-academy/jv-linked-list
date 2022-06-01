package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(last, value,null);
        if (size == 0) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> currentNode = new Node<>(null, value, first);
            first.prev = currentNode;
            first = currentNode;
        } else {
            Node<T> oldNode = findNode(index);
            Node<T> currentNode = new Node<>(oldNode.prev, value, oldNode);
            oldNode.prev.next = currentNode;
            oldNode.prev = currentNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = findNode(index);
        T oldNodeValue = oldNode.item;
        oldNode.item = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> oldNode = findNode(index);
        T oldNodeItem = oldNode.item;
        unlink(oldNode);
        return oldNodeItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object == null ? node.item == null : object.equals(node.item)) {
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
        return size == 0;
    }

    private void validIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index" + index + "is invalid");
        }
    }

    private Node<T> findNode(int index) {
        validIndex(index);
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = null;
            last = null;
        } else if (node == first) {
            first.next.prev = null;
            first = node.next;
        } else if (node == last) {
            last.prev.next = null;
            last = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
