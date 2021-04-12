package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;

    private class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        if (first == null) {
            first = new Node<>(null, value, null);
            return true;
        }
        Node<T> element = first;
        while (element.next != null) {
            element = element.next;
        }
        Node<T> newNode = new Node<T>(element, value, null);
        element.next = newNode;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (first == null) {
            add(value);
            return;
        }
        Node<T> currentNode = getNodeByIndex(index);

        if (currentNode == null) {
            Node<T> prevCurrent = getNodeByIndex(index - 1);
            if (prevCurrent == null) {
                throw new IndexOutOfBoundsException("Index is bigger than list size");
            }
            add(value);
            return;
        }

        Node<T> prevCurrent = currentNode.prev;
        Node<T> newNode = new Node<>(prevCurrent, value, currentNode);
        if (prevCurrent != null) {
            prevCurrent.next = newNode;
        } else {
            first = newNode;
        }
        currentNode.prev = newNode;
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("List is empty!");
        }
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        if (newNode == null) {
            throw new IndexOutOfBoundsException("Index is bigger, than list size");
        }
        return newNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNodeByIndex(index);
        if (newNode == null) {
            throw new IndexOutOfBoundsException("Index was " + index + " and the size is "
                    + size());
        }
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        var currentNode = getNodeByIndex(index);
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("Index was " + index + " and the size is "
                    + size());
        }
        if (index == 0) {
            var nextCurrent = currentNode.next;
            if (nextCurrent != null) {
                nextCurrent.prev = null;
            }
            first = nextCurrent;
            return currentNode.item;
        }
        if (currentNode.next == null) {
            var prevCurrent = currentNode.prev;
            prevCurrent.next = null;
            return currentNode.item;
        }
        var prevCurrent = currentNode.prev;
        prevCurrent.next = currentNode.next;
        var nextCurrent = currentNode.next;
        nextCurrent.prev = currentNode.prev;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        var i = getIndexByValue(object);
        if (i == -1) {
            return false;
        }
        remove(i);
        return true;
    }

    @Override
    public int size() {
        int i = 0;
        for (Node<T> element = first; element != null; element = element.next) {
            i++;
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        int i = 0;
        for (Node<T> element = first; element != null; element = element.next) {
            if (i == index) {
                return element;
            }
            i++;
        }
        return null;
    }

    private int getIndexByValue(T object) {
        int i = 0;
        for (Node<T> element = first; element != null; element = element.next) {
            if (element.item == null && object == null
                    || element.item != null && element.item.equals(object)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index should be >= 0, index was " + index);
        }
    }
}
