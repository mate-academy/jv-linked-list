package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private int size;
    private Node<T> head;

    public MyLinkedList() {
        tail = null;
        size = 0;
        head = null;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNextNode = neededNode(index);
        Node<T> newBeforeNode = newNextNode.previous;
        Node<T> newNode = new Node<>(newBeforeNode, value, newNextNode);
        newNextNode.previous = newNode;
        if (newBeforeNode == null) {
            head = newNode;
        } else {
            newBeforeNode.next = newNode;
        }
        size++;
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
        checkIndex(index, size - 1);
        return neededNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> setNode = neededNode(index);
        T resetValue = setNode.data;
        setNode.data = value;
        return resetValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> removedNode = neededNode(index);
        Node<T> beforeRemovedNode = removedNode.previous;
        Node<T> nextRemovedNode = removedNode.next;
        if (beforeRemovedNode == null) {
            head = nextRemovedNode;
        } else {
            beforeRemovedNode.next = nextRemovedNode;
        }
        if (nextRemovedNode == null) {
            tail = beforeRemovedNode;
        } else {
            nextRemovedNode.previous = beforeRemovedNode;
        }
        size--;
        return removedNode.data;
    }

    @Override
    public boolean remove(T t) {
        Node<T> removedNode = head;
        for (int i = 0; i < size; i++) {
            if (removedNode.data == t || t != null && t.equals(removedNode.data)) {
                remove(i);
                return true;
            }
            removedNode = removedNode.next;
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

    private Node<T> neededNode(int index) {
        Node<T> need;
        if (index <= size / 2) {
            need = head;
            for (int i = 0; i < index; i++) {
                need = need.next;
            }
        } else {
            need = tail;
            for (int i = size - 1; i > index; i--) {
                need = need.previous;
            }
        }
        return need;
    }

    private void checkIndex(int index, int condition) {
        if (index < 0 || index > condition) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class Node<E> {
        Node<E> previous;
        E data;
        Node<E> next;

        private Node(Node<E> previous, E data, Node<E> next) {
            this.previous = previous;
            this.data = data;
            this.next = next;
        }

        public Node(E data) {
            this.data = data;
        }
    }
}
