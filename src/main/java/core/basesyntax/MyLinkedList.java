package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()){
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        }
        Node<T> newNode = new Node<>(node(index).prev, value, node(index));
        node(index).prev.next = newNode;
        node(index).prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T temp = node(index).value;
        node(index).value = value;
        return temp;
    }

    @Override
    public T remove(int index) {    //////////////////// ----------> /////////////////////
        checkIndex(index);
        T temp = node(index).value;
        if (node(index).prev == null) {
            node(index).next.prev = null;
            unlink(node(index));
            size--;
        }
        else if (node(index).next == null) {
            node(index).prev.next = null;
            unlink(node(index));
            size--;
        }
        else {
            node(index).next.prev = node(index).prev;
            node(index).prev.next = node(index).next;
            unlink(node(index));
            size--;
        }
        return temp;
    }

    @Override
    public boolean remove(T object) {
       // throw new NoSuchElementException("Item not found!");
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

    private void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
           throw new IndexOutOfBoundsException("Index is out of bounds " + index + "!");
        }
    }

    private Node<T> node(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        node.prev = null;
        node.next = null;
        node.value = null;
    }
}

class Node<T> {
    T value;
    Node<T> next;
    Node<T> prev;

    public Node(Node<T> prev, T value, Node<T> next) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
}
