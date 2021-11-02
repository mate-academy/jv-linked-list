package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.setNext(newNode);
            tail = newNode;
        }
        size = size + 1;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> oldNode = getNode(index);
            Node<T> newNode = new Node<>(oldNode.getPrev(), value, oldNode);
            if (index != 0) {
                oldNode.getPrev().setNext(newNode);
            } else {
                head = newNode;
            }
            oldNode.setPrev(newNode);
            size = size + 1;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T returnValue = node.getValue();
        node.setValue(value);
        return returnValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T returnValue = node.getValue();
        unlink(node);
        size = size - 1;
        return returnValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node != null) {
            unlink(node);
            size = size - 1;
            return true;
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

    private void unlink(Node<T> node) {
        if (node != head) {
            node.getPrev().setNext(node.getNext());
        } else {
            head = node.getNext();
        }
        if (node != tail) {
            node.getNext().setPrev(node.getPrev());
        } else {
            tail = node.getPrev();
        }
    }

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node;
    }

    private Node<T> getNode(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.getValue() == value) {
                return node;
            }
            if (value != null && value.equals(node.getValue())) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }
}
