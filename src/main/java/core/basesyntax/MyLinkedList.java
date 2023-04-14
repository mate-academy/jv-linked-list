package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexPosition(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> target = getNode(index);
            linkBefore(value, target);
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
        checkElementIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.value == object
                    || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                unlink(currentNode);
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

    private T unlink(Node<T> node) {
        final T element = node.value;
        Node<T> next = node.next;
        Node<T> previous = node.prev;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
            node.next = null;
        }
        node.value = null;
        size--;
        return element;
    }

    private void checkIndexPosition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index "
                    + index
                    + " is out of bounds. Please enter correct index.");
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index "
                    + index
                    + " is out of bounds. Please enter correct index.");
        }
    }

    private void linkBefore(T value, Node<T> succ) {
        final MyLinkedList.Node<T> pred = succ.prev;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(pred, value, succ);
        succ.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        checkIndexPosition(index);
        if (index < (size / 2)) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private static class Node<E> {
        private Node<E> prev;
        private E value;
        private Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.next = next;
            this.value = element;
        }
    }
}
