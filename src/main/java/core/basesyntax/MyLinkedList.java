package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirst(value);
            return;
        }
        Node<T> newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != 0) {
            indexCheck(index - 1);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        Node<T> current = getByIndex(index);
        Node<T> prevNode = current.prev;
        Node<T> newNode = new Node<>(prevNode, value, current);
        prevNode.next = newNode;
        current.prev = newNode;
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
        indexCheck(index);
        return getByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> current = getByIndex(index);
        T deletingValue = current.item;
        current.item = value;
        return deletingValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> current = getByIndex(index);
        if (size == 1) {
            removeLast();
            return current.item;
        }
        unLink(current);
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = getNodeByValue(object);
        if (current == null) {
            return false;
        }
        if (size == 1) {
            removeLast();
            return true;
        }
        unLink(current);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addFirst(T element) {
        Node<T> current = first;
        if (isEmpty()) {
            Node<T> newNode = new Node<>(null, element, null);
            first = newNode;
            last = newNode;
        } else {
            first = new Node<>(null, element, current);
        }
        size++;
    }

    private void removeLast() {
        first = null;
        last = null;
        size = 0;
    }

    private void unLink(Node<T> current) {
        Node<T> nextNode = current.next;
        Node<T> prevNode = current.prev;
        if (current.equals(first)) {
            nextNode.prev = null;
            first = nextNode;
        } else if (current.equals(last)) {
            prevNode.next = null;
            last = prevNode;
        } else {
            nextNode.prev = current.prev;
            prevNode.next = current.next;
        }
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> current = first;
        while (current != null
              && !(current.item == value || current.item != null && current.item.equals(value))) {
            current = current.next;
        }
        return current;
    }

    private Node<T> getByIndex(int index) {
        Node<T> current;
        if (index <= size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid!");
        }
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
