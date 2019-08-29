/* Дано класс MyLinkedList который имплементирует интерфейс MyLinkedListInterface.
    Реализовать в нём свой LinkedList  */

package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is out of range: " + index);
        }

        Node<T> nodeIndex = first;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                nodeIndex = nodeIndex.next;
            }
        } else {
            nodeIndex = last;
            for (int i = size - 1; i > index; i--) {
                nodeIndex = nodeIndex.previous;
            }
        }
        return nodeIndex;
    }

    private void addToEmpty(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        first = newNode;
        last = newNode;
        size++;
    }

    public void addAsFirst(T value) {
        if (size == 0) {
            addToEmpty(value);
            return;
        }
        Node<T> newNode = new Node<>(value, null, first);
        first.previous = newNode;
        first = newNode;
        size++;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addToEmpty(value);
            return;
        }
        Node<T> newNode = new Node<>(value, last, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is out of range: " + index);
        }
        if (size == 0) {
            addToEmpty(value);
            return;
        }
        if (index == 0) {
            addAsFirst(value);
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value, getNode(index - 1), getNode(index + 1));
        getNode(index - 1).next = newNode;
        getNode(index + 1).previous = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            this.add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is out of range: " + index);
        }
        return getNode(index).data;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is out of range: " + index);
        }
        getNode(index).data = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is out of range: " + index);
        }

        final Node<T> removedElement = getNode(index);
        if (removedElement.previous == null) {
            first = removedElement.next;
        } else {
            removedElement.previous.next = removedElement.next;
            removedElement.previous = null;
        }

        if (removedElement.next == null) {
            last = removedElement.previous;
        } else {
            removedElement.next.previous = removedElement.previous;
            removedElement.next = null;
        }

        T dataToRemove = removedElement.data;
        removedElement.data = null;
        size--;
        return dataToRemove;
    }

    @Override
    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).data == t) {
                T dataOfRemoved = getNode(i).data;
                remove(i);
                return dataOfRemoved;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder listToString = new StringBuilder();
        listToString.append("[ ");
        for (int i = 0; i < size; i++) {
            listToString.append(get(i));
            listToString.append(" ");
        }
        listToString.append("]");
        return listToString.toString();
    }

    private static class Node<T> {
        private T data;
        private Node<T> previous;
        private Node<T> next;

        private Node(T data, Node<T> previous, Node<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }
}
