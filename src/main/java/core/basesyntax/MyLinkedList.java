package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;

    @Override
    public void add(T value) {
        Node<T> temp = new Node<T>(tail, value, null);
        if (size == 0) {
            head = temp;
        } else {
            tail.next = temp;
        }
        tail = temp;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            Node<T> temp = getNodeByIndex(index);
            Node<T> tempPrev = temp.prev;
            Node<T> current = new Node<T>(tempPrev, value, temp);
            temp.prev = current;
            if (index == 0) {
                head = current;
            }
            if (tempPrev != null) {
                tempPrev.next = current;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> temp = getNodeByIndex(index);
        return temp.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> temp = getNodeByIndex(index);
        T returnValue = temp.value;
        temp.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> temp = getNodeByIndex(index);
        T removedValue = temp.value;
        unLink(temp);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> temp = head;
        while (temp != null) {
            if (temp.value == object || temp.value != null && temp.value.equals(object)) {
                unLink(temp);
                size--;
                return true;
            }
            temp = temp.next;
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

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index [" + index
                    + "] is out of List range");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> temp;
        int counter;
        if (index <= size / 2) {
            temp = head;
            counter = 0;
            while (counter < index) {
                temp = temp.next;
                counter++;
            }
        } else {
            temp = tail;
            counter = size - 1;
            while (counter > index) {
                temp = temp.prev;
                counter--;
            }
        }
        return temp;
    }

    private void unLink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            Node<T> tempNext = node.next;
            tempNext.prev = null;
            head = node.next;
        } else if (node.next == null) {
            Node<T> tempPrev = node.prev;
            tempPrev.next = null;
            tail = node.prev;
        } else {
            Node<T> tempPrev = node.prev;
            Node<T> tempNext = node.next;
            tempPrev.next = tempNext;
            tempNext.prev = tempPrev;
            node.prev = null;
            node.next = null;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
