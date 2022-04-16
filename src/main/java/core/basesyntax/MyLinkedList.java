package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;

    private class Node {
        int value;
        Node prev;
        Node next;

        Node(int x) {
            value = x;
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node((int) value);
            last = first;
            return;
        }
        Node tempNode = new Node((int) value);
        tempNode.prev = last;
        tempNode.next = null;
        last.next = tempNode;
        last = tempNode;
    }

    @Override
    public void add(T value, int index) {
        (int i = 0; i <= index; i++) {
            Object o =  list.get(i);

        })
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
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
        if (first == null) {
            return true;
        }
        return false;
    }
}
