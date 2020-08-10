package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Not in range");
        }
        if (index == size) {
            addLast(value);
        } else {
            addBefore(value, index);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> tempNode = getNode(index);
        T result = tempNode.value;
        tempNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> tempNode = getNode(index);
        T result = tempNode.value;
        if (size == 1) {
            first = null;
            last = null;
            size--;
            return result;
        }
        if (tempNode.previous == null) {
            first = tempNode.next;
            tempNode.next.previous = null;
            size--;
            return result;
        }
        if (tempNode.next == null) {
            last = tempNode.previous;
            last.next = null;
            size--;
            return result;
        }
        tempNode.previous.next = tempNode.next;
        tempNode.next.previous = tempNode.previous;
        size--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        Node<T> tempNode = first;
        for (int i = 0; i < size; i++) {
            if (tempNode.value == t || tempNode.value != null && tempNode.value.equals(t)) {
                remove(i);
                return true;
            }
            tempNode = tempNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not in range of the list!");
        }
    }

    private void addBefore(T value, int index) {
        Node<T> tempNode = getNode(index);
        Node<T> newNode = new Node<>(value, tempNode.previous, tempNode);
        if (tempNode.previous == null) {
            newNode.next = tempNode;
            tempNode.next.previous = newNode;
            first = newNode;
        } else {
            newNode.next = tempNode;
            newNode.previous = tempNode.previous;
            tempNode.previous.next = newNode;
            tempNode.previous = newNode;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (first == null) {
            newNode.previous = null;
            newNode.next = null;
            first = newNode;
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> tempNode = first;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        return tempNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }
}
