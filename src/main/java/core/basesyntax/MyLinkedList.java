package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;

    @Override
    public void add(T value) {
        if (size == 0) {
            firstElement(value);
        } else {
            Node<T> temp = new Node<T>(tail, value, null);
            tail.next = temp;
            tail = temp;
            size++;
        }
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

    public Node<T> getNodeByIndex(int index) {
        Node<T> temp = head;
        int counter = 0;
        while (counter < index) {
            temp = temp.next;
            counter++;
        }
        return temp;
    }

    public void firstElement(T value) {
        Node<T> temp = new Node<T>(null, value, null);
        head = temp;
        tail = temp;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
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
        if (size == 1) {
            head = null;
            tail = null;
        } else if (temp.prev == null) {
            Node<T> tempNext = temp.next;
            tempNext.prev = null;
            head = temp.next;
        } else if (temp.next == null) {
            Node<T> tempPrev = temp.prev;
            tempPrev.next = null;
            tail = temp.prev;
        } else {
            Node<T> tempPrev = temp.prev;
            Node<T> tempNext = temp.next;
            tempPrev.next = tempNext;
            tempNext.prev = tempPrev;
            temp.prev = null;
            temp.next = null;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        int counter = 0;
        Node<T> temp = head;
        while (counter < size) {
            if (temp.value == object || temp.value != null && temp.value.equals(object)) {
                remove(counter);
                return true;
            }
            temp = temp.next;
            counter++;
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

    class Node<T> {
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
