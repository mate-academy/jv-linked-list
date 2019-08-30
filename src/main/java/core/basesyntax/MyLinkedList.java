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

    public void addFirst(T value) {
        final Node<T> firstNode = first;
        final Node<T> newNode = new Node<>(value, null, firstNode);
        first = newNode;
        if (firstNode == null) {
            last = newNode;
        } else {
            firstNode.previous = newNode;
        }
        size++;
    }

    @Override
    public void add(T value) {
        final Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(value, lastNode, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            addFirst(value);
        }
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
            throw new IndexOutOfBoundsException();
        }

        final Node<T> removedElement = getNode(index);
        final T dataToRemove = removedElement.data;
        Node<T> next = removedElement.next;
        Node<T> previous = removedElement.previous;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            previous = null;
        }

        if (next == null) {
            last = previous;
        } else {
            next.previous = previous;
            next = null;
        }

        removedElement.data = null;
        size--;
        return dataToRemove;
    }

    @Override
    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).data.equals(t)) {
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
