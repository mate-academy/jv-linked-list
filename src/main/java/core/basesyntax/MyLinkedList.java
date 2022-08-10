package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    @Override
    public void add(T value) {
        Node<T> temp = tail;
        Node<T> newNode = new Node<>(temp, value, null);
        tail = newNode;
        if (temp == null) {
            head = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
    }
    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", is out of bound!");
        }
        if(index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> temp = head;
            Node<T> newNode = new Node<>(null, value, temp);
            head = newNode;
            if (temp != null) {
                temp.prev = newNode;
            }
        }
        else {
            Node<T> temp = getNode(index);
            Node<T> newNode = new Node<>(temp.prev, value, temp);
            temp.prev.next = newNode;
            temp.prev = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", is out of bound!");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }
    private class Node<T>{
        Node<T> next;
        Node<T> prev;
        T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }
}
