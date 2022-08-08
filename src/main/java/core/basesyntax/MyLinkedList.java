package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index == size) {
            Node<T> lastNode = last;
            Node<T> newNode = new Node<>(lastNode, value, null);
            if (first == null) {
                first = newNode;
            } else {
                last.next = newNode;
            }
            last = newNode;
            size++;
        } else if (index > 0) {
            Node<T> newNode = first;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            Node<T> currentNode = new Node<>(newNode.prev, value, newNode);
            currentNode.prev.next = currentNode;
            currentNode.next.prev = currentNode;
            size++;
        } else {
            Node<T> newNode = first;
            Node<T> currentNode = new Node<>(null, value, newNode);
            newNode.prev = currentNode;
            first = currentNode;
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
        checkIndex(index);
        Node<T> newNode = findNodeByIndex(index);
        return newNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = findNodeByIndex(index);
        T oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> newNode = findNodeByIndex(index);
        if (first == last) {
            first = null;
            last = null;
        } else if (newNode == first) {
            newNode.next.prev = newNode.prev;
            first = newNode.next;
        } else if (newNode == last) {
            newNode.prev.next = newNode.next;
            last = newNode.prev;
        } else {
            newNode.prev.next = newNode.next;
            newNode.next.prev = newNode.prev;
        }
        size--;
        return newNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = first;
        int index = 0;
        while (newNode != null) {
            if (newNode.value == null && object == null || newNode.value.equals(object)) {
                remove(index);
                return true;
            }
            newNode = newNode.next;
            index++;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> newNode = first;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        return newNode;
    }
}
