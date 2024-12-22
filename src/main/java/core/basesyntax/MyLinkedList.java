package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAddedElement(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> succ = head;
            int i = 0;
            while (index != i) {
                succ = succ.next;
                i++;
            }
            Node<T> pred = succ.prev;
            Node<T> newNode = new Node<>(pred, value, succ);
            succ.prev = newNode;
            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
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
        checkIndex(index);
        Node<T> currNode;
        if (index < (size >> 1)) {
            currNode = head;
            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
        } else {
            currNode = tail;
            for (int i = size - 1; i > index; i--) {
                currNode = currNode.prev;
            }
        }
        return currNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currNode = head;
        for (int i = 0; i <= index; i++) {
            if (index == i) {
                T oldItem = currNode.item;
                currNode.item = value;
                return oldItem;
            }
            currNode = currNode.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToDelete = head;
        for (int i = 0; i <= index; i++) {
            if (index == i) {
                Node<T> next = nodeToDelete.next;
                Node<T> prev = nodeToDelete.prev;
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = nodeToDelete.next;
                    nodeToDelete.next = null;
                }
                if (next == null) {
                    tail = prev;
                } else {
                    next.prev = nodeToDelete.prev;
                    nodeToDelete.prev = null;
                }
                T element = nodeToDelete.item;
                nodeToDelete.item = null;
                size--;
                return element;
            }
            nodeToDelete = nodeToDelete.next;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        int indexToRemove = 0;
        Node<T> currItem = head;
        while (currItem != null) {
            if (Objects.equals(currItem.item, object)) {
                remove(indexToRemove);
                return true;
            }
            currItem = currItem.next;
            indexToRemove++;
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
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndexForAddedElement(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index "
                    + index + " is out of bounds for list size " + size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index "
                    + index + " is out of bounds for list size " + size);
        }
    }
}
