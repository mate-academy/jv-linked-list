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
    public void add(T value) {
        if (size == 0) {
            addFirstNode(value);
        } else {
            Node<T> node = new Node<>(last, value, null);
            last.next = node;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> node = getNode(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNode(index).item;
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        removeNode(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
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

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index entered incorrectly");
        }
        Node<T> node = (index >= (size / 2) ? last : first);
        int countIter = (index >= (size / 2) ? size - index - 1 : index);
        for (int i = 0; i < countIter; i++) {
            node = (index >= (size / 2) ? node.prev : node.next);
        }
        return node;
    }

    private void removeNode(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
    }

    private void addFirstNode(T value) {
        Node<T> node = new Node<>(null, value, null);
        first = node;
        last = node;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
