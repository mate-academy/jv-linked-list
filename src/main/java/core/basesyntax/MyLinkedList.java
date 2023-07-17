package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addInHead(value);
        } else {
            addInMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        T prevValue = current.value;
        current.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = getNodeByIndex(index);
        unlink(removed);
        size--;
        return removed.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == current.value) || (object != null && object.equals(current.value))) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
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
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
    
    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        return (index < size / 2) ? searchFromHead(index) : searchFromTail(index);
    }

    private void addInHead(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void addInMiddle(T value, int index) {
        Node<T> newCurrent = new Node<>(null, value, null);
        Node<T> newNext = getNodeByIndex(index);
        Node<T> previous = newNext.prev;
        newCurrent.next = newNext;
        newCurrent.prev = previous;
        newNext.prev = newCurrent;
        previous.next = newCurrent;
        size++;
    }

    private Node<T> searchFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private class Node<T> {
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
