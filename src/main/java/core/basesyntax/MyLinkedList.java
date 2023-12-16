package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(first, value, null);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Incorrect index " + index + "; size is " + size());
        }
        if (index == size()) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> currentNode = findNodeByIndex(index);

            if (index == 0) {
                newNode.next = first;
                first.prev = newNode;
                first = newNode;
            } else {
                newNode.next = currentNode;
                newNode.prev = currentNode.prev;
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;
            }
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
        Node<T> currentNode = findNodeByIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.item;
        unlinkNode(currentNode);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size(); i++) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
                unlinkNode(currentNode);
                size--;
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
        return size() == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Incorrect index " + index + "; size is " + size());
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < (size() / 2)) {
            currentNode = first;
            for (int currentIndex = 0; currentIndex < index; currentIndex++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int currentIndex = size() - 1; currentIndex > index; currentIndex--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unlinkNode(Node<T> node) {
        if (node.prev == null && node.next == null) {
            first = last = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
