package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        private Node(T element, Node<T> next, Node<T> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public void add(T value) {
        if (this.isEmpty()) {
            Node newNode = new Node(value, last, first);
            first = newNode;
            last = newNode;
        } else {
            last = new Node<>(value, null, last);
            last.previous.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(value, getNode(index), getNode(index).previous);
            newNode.previous.next = newNode;
            newNode.next.previous = newNode;
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
        Node<T> localNode = getNode(index);
        return localNode.element;
    }

    @Override
    public void set(T value, int index) {
        Node<T> localNode = getNode(index);
        localNode.element = value;

    }

    @Override
    public T remove(int index) {
        Node<T> localNode = getNode(index);
        T result = localNode.element;
        localNode.element = null;
        if (first.equals(last)) {
            size--;
            return result;
        }
        if (index == 0) {
            first = localNode.next;
            localNode.next.previous = null;
            localNode.next =  null;
            size--;
            return result;
        } else if (index == size - 1) {
            last = localNode.previous;
            localNode.previous.next = null;
            localNode.previous = null;
            size--;
            return result;
        }
        localNode.previous.next = localNode.next;
        localNode.next.previous = localNode.previous;
        localNode.next = localNode.previous = null;
        size--;
        return result;
    }

    @Override
    public T remove(T t) {
        Node<T> localNode;
        localNode = first;
        int counter = 0;
        while (!Objects.equals(t, localNode.element)) {
            if (localNode.equals(last)) {
                return null;
            }
            localNode = localNode.next;
            counter++;
        }
        return remove(counter);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private  Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> localNode;
        if (index < (size >> 1)) {
            localNode = first;
            for (int i = 0; i < index; i++) {
                localNode = localNode.next;
            }
        } else {
            localNode = last;
            for (int i = size - 1; i > index; i--) {
                localNode = localNode.previous;
            }
        }
        return localNode;
    }
}
