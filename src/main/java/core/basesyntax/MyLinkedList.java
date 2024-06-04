package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAddMethod(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            newNode.next.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> previousToNew = findNodeByIndex(index - 1);
            newNode.next = previousToNew.next;
            newNode.prev = previousToNew;
            previousToNew.next = newNode;
            newNode.next.prev = newNode;
        }
        size++;
    }

    private void checkIndexForAddMethod(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return findNodeByIndex(index).getItem();
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.getItem();
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.getNext()) {
            if (object == null ? x.getItem() == null : object.equals(x.getItem())) {
                unlink(x);
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

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.getPrev();
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        final T element = node.getItem();
        final Node<T> next = node.getNext();
        final Node<T> prev = node.getPrev();

        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
            node.setPrev(null);
        }

        if (next == null) {
            tail = prev;
        } else {
            next.setPrev(prev);
            node.setNext(null);
        }

        node.setItem(null);
        size--;
        return element;
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
}
