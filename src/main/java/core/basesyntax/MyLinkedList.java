package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> searchNode = searchNodeByIndex(index);
        Node<T> addNode = new Node<>(searchNode.prev, value, searchNode);
        if (index == 0) {
            head = addNode;
        } else {
            searchNode.prev.next = addNode;
        }
        searchNode.prev = addNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        Node<T> search = searchNodeByIndex(index);
        return search.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = searchNodeByIndex(index);
        T returnValue = setNode.item;
        setNode.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = searchNodeByIndex(index);
        return unlink(removeNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
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

    private void chekIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private Node<T> searchNodeByIndex(int index) {
        chekIndex(index);
        Node<T> search;
        if (index <= size / 2) {
            search = head;
            for (int i = 0; i < index; i++) {
                search = search.next;
            }
        } else {
            search = tail;
            for (int i = size - 1; i > index; i--) {
                search = search.prev;
            }
        }
        return search;
    }

    public static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.item = value;
            this.next = next;
        }
    }

    private T unlink(Node<T> removeNode) {
        if (head.equals(removeNode)) {
            if (size == 1) {
                head = removeNode.next;
                tail = removeNode.next;
            }
            head = removeNode.next;
        } else if (removeNode.next != null) {
            removeNode.prev.next = removeNode.next;
            removeNode.next.prev = removeNode.prev;
        } else {
            tail = removeNode.prev;
            removeNode.prev.next = null;
        }
        size--;
        return removeNode.item;
    }
}
