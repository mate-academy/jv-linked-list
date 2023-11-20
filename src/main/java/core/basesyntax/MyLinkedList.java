package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            insertAtTheEndOfTheList(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            insertAtTheBeginningOfTheList(value);
        } else {
            insertAtTheMiddleOfTheList(value, index);
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
        checkIndex(index, size);
        Node<T> getElement = findNodeByIndex(index);
        return getElement.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> overwriteValue = findNodeByIndex(index);
        T oldValue = overwriteValue.item;
        overwriteValue.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> removedNode = findNodeByIndex(index);
        return unlink(removedNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object || (object != null && object.equals(currentNode.item))) {
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
        return size() == 0;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index, size);
        if (index < (size >> 1)) {
            Node<T> searchNodeFromHead = head;
            for (int i = 0; i < index; i++) {
                searchNodeFromHead = searchNodeFromHead.next;
            }
            return searchNodeFromHead;
        } else {
            Node<T> searchNodeFromTail = tail;
            for (int i = size - 1; i > index; i--) {
                searchNodeFromTail = searchNodeFromTail.prev;
            }
            return searchNodeFromTail;
        }
    }

    private void insertAtTheBeginningOfTheList(T value) {
        Node<T> newNode = new Node<>(value);
        head.prev = newNode;
        newNode.next = head;
        head = newNode;
    }

    private void insertAtTheEndOfTheList(T value) {
        Node<T> newNode = new Node<>(value);
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    private void insertAtTheMiddleOfTheList(T value, int index) {
        checkIndex(index, size);
        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        newNode.next = currentNode;
        newNode.prev = currentNode.prev;
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
    }

    private T unlink(Node<T> node) {
        if (node.prev == null) {
            head.prev = null;
            head = node.next;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        T element = node.item;
        size--;
        return element;
    }

    public void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Enter the correct index");
        }
    }
}
