package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value) {
            this.prev = prev;
            this.value = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index can`t be:" + index
                    + " for LinkedList size:" + size);
        }
        Node<T> nodeByIndex = iterateToIndex(index);
        if (nodeByIndex == null) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
        if (head == nodeByIndex) {
            head = newNode;
        } else {
            nodeByIndex.prev.next = newNode;
        }
        nodeByIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (Object element : list) {
            add((T) element);
        }
    }

    @Override
    public T get(int index) {
        checkOutOfMemory(index);
        Node<T> nodeByIndex = iterateToIndex(index);
        return (T) nodeByIndex.value;
    }

    @Override
    public T set(T value, int index) {
        checkOutOfMemory(index);
        Node<T> nodeByIndex = iterateToIndex(index);
        final Object previousValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return (T) previousValue;
    }

    @Override
    public T remove(int index) {
        checkOutOfMemory(index);
        Node<T> removedNode = iterateToIndex(index);
        final Object removedValue = removedNode.value;
        unlink(removedNode);
        return (T) removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node tmp = head;
        while (tmp != null) {
            if (tmp.value == object || (tmp.value != null && tmp.value.equals(object))) {
                unlink(tmp);
                return true;
            }
            tmp = tmp.next;
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

    private void unlink(Node<T> tmp) {
        if (tmp != tail && tmp != head) {
            tmp.prev.next = tmp.next;
            tmp.next.prev = tmp.prev;
        }
        if (tmp == head) {
            head = tmp.next;
        }
        if (tmp == tail) {
            tail = tmp.prev;
        }
        size--;
    }

    private Node<T> iterateToIndex(int index) {
        Node tmp = head;
        for (int i = 0; i < index; i++) { // use head/tail
            tmp = tmp.next;
        }
        return tmp;
    }

    private void checkOutOfMemory(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index can`t be:" + index
                    + " for LinkedList size:" + size);
        }
    }
}
