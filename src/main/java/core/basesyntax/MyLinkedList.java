package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, tail);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentItem = findNodeByIndex(index);
        Node<T> newNode = new Node<>(
                value,
                currentItem,
                currentItem.getPrev()
        );
        if (currentItem.getPrev() == null) {
            head = newNode;
        } else {
            currentItem.getPrev().setNext(newNode);
        }
        currentItem.setPrev(newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).getItem();
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentItem = findNodeByIndex(index);
        T oldValue = currentItem.getItem();
        currentItem.setItem(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentItem = head; currentItem != null; currentItem = currentItem.getNext()) {
            if (Objects.equals(currentItem.getItem(), object)) {
                unlink(currentItem);
                return true;
            }
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

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        if (index < size / 2) {
            Node<T> currentItem = head;
            for (int i = 0; i < index; i++) {
                currentItem = currentItem.getNext();
            }
            return currentItem;
        } else {
            Node<T> currentItem = tail;
            for (int i = size - 1; i > index; i--) {
                currentItem = currentItem.getPrev();
            }
            return currentItem;
        }
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.getPrev();
        Node<T> next = node.getNext();
        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
        }
        if (next == null) {
            tail = prev;
        } else {
            next.setPrev(prev);
        }
        size--;
        return node.getItem();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
