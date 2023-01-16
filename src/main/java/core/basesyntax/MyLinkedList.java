package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> had;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(value, null, null);
            had = node;
            tail = node;
        } else {
            Node<T> node = new Node<>(value, tail, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && index <= size) {
            if (size == index) {
                add(value);
                return;
            }
            Node<T> pointer = getNodeByIndex(index);
            Node<T> newNode = new Node<>(value, null, pointer);
            if (pointer == had) {
                had.privies = newNode;
                had = newNode;
            } else {
                newNode.privies = pointer.privies;
                pointer.privies.next = newNode;
                pointer.privies = newNode;
            }
            size++;
            return;
        }
        throw new IndexOutOfBoundsException("Invalid index: " + index);
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) { // getNodeByIndex
        indexValid(index);
        Node<T> node = getNodeByIndex(index);
        return (T) node.value;
    }

    @Override
    public T set(T value, int index) {
        indexValid(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexValid(index);
        Node<T> node = (Node<T>) getNodeByIndex(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> pointer = had;
        while (pointer != null) {
            if (pointer.value == object
                    || pointer.value != null && pointer.value.equals(object)) {
                unlink(pointer);
                return true;
            }
            pointer = pointer.next;
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

    private Node<T> getNodeByIndex(int index) {
        if (index <= size / 2) {
            Node<T> pointer = had;
            for (int i = 0; i < index; i++) {
                pointer = pointer.next;
            }
            return pointer;
        } else {
            Node<T> pointer = tail;
            for (int i = size - 1; i > index; i--) {
                pointer = pointer.privies;
            }
            return pointer;
        }
    }

    private T unlink(Node<T> node) {
        if (node == had) {
            had = had.next;
            node.privies = null;
            size--;
            return (T) node.value;
        }
        if (node == tail) {
            tail = tail.privies;
            node.next = null;
            size--;
            return (T) node.value;
        } else {
            node.privies.next = node.next;
            node.next.privies = node.privies;
            size--;
            return (T) node.value;
        }
    }

    private boolean indexValid(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        throw new IndexOutOfBoundsException("Invalid index: " + index);
    }

    private static class Node<T> {
        private T value;
        private Node<T> privies;
        private Node<T> next;

        Node(T value, Node<T> privies, Node<T> next) {
            this.value = value;
            this.privies = privies;
            this.next = next;
        }

    }
}
