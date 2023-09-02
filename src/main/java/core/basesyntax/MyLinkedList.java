package core.basesyntax;

import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;

    private int size;

    private static class Node<T> {
        T element;
        Node next;
        Node previous;

        public Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void add(T element) {
        Node newNode = new Node(element);
        if (first == null) {
            newNode.next = null;
            newNode.previous = null;
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T element, int index) {
        if (index < 0 || index > size) {
           throw new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node(element);
        if (index == 0) {
            add(element);
        } else if (index == size) {
            Node<T> oldNode = last;
            oldNode.next = newNode;
            newNode.previous = oldNode;
            last = newNode;

            newNode.next = null;
            size++;
        } else {
            Node<T> oldNode = first;
            for (int i = 0; i < index; i++) {
                oldNode = oldNode.next;
            }
            Node<T> oldPrevious = oldNode.previous;
            oldPrevious.next = newNode;
            oldNode.previous = newNode;

            newNode.previous = oldPrevious;
            newNode.next = oldNode;
            size++;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }

        return result.element;
    }

    @Override
    public T set(T value, int index) {

        Node<T> node = first;
        int count = 0;
        while(node.next != null) {
            count++;
            if(count == index){
                node.element = value;
            }
            node = node.next;
        }
        return node.element;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;

    }



    @Override
    public T remove(int index) {

        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }
        Node<T> result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        T element = result.element;

        Node<T> prev = result.previous;
        Node<T> next = result.next;
        if (prev == null) {
            next.previous = null;
        } else {
            result.previous.next = next;
            result.next.previous = prev;
        }

        return element;
    }

    @Override
    public boolean remove(T object) {
            if (size == 0) {
                return false;
            } else if (size == 1) {
                first = null;
                last = null;
                size = 0;
                return true;
            }
        return false;
    }

    @Override
    public int size() {
        return size;
    }


}
