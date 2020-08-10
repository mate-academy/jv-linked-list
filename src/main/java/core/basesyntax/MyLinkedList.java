package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int elementsNumber;

    @Override
    public boolean add(T value) {
        Node<T> current = new Node<T>(tail, value, null);
        if (elementsNumber == 0) {
            head = current;
        } else {
            tail.next = current;
        }
        tail = current;
        elementsNumber++;
        return true;

    }

    @Override
    public void add(T value, int index) {
        if (index == elementsNumber) {
            add(value);
            return;
        }
        checkElementIndex(index);
        Node<T> oldNode = node(index);
        Node<T> newNode = new Node<>(oldNode.previous, value, oldNode);
        if (index > 0) {
            newNode.previous.next = newNode;
        } else {
            head = newNode;
        }
        oldNode.previous = newNode;
        elementsNumber++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> x = node(index);
        T oldVal = x.element;
        x.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> node = node(index);
        if (index > 0) {
            node.previous.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            tail = node.previous;
        }
        T removedItem = node.element;
        elementsNumber--;
        return removedItem;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = head;
        for (int i = 0; i < elementsNumber; i++) {
            if (node.element == t || node.element == t || node.element.equals(t)) {
                remove(i);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return elementsNumber;
    }

    @Override
    public boolean isEmpty() {
        return elementsNumber == 0;
    }

    private class Node<T> {
        T element;
        Node<T> next;
        Node<T> previous;

        Node(Node<T> previous, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node<T> node(int index) {

        Node<T> x;
        if (index < (elementsNumber >> 1)) {
            x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = tail;
            for (int i = elementsNumber - 1; i > index; i--) {
                x = x.previous;
            }
        }
        return x;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= elementsNumber) {
            throw new IndexOutOfBoundsException();
        }
    }
}
