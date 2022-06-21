package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node(tail, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else {
            checkIndex(index);
            Node<T> currentNode = findByIndex(index);
            Node<T> prevNode = currentNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, currentNode);
            currentNode.prev = newNode;
            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findByIndex(index);
        T values = current.element;
        current.element = value;
        return values;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> deletedIndex = findByIndex(index);
        clearLink(deletedIndex);
        return deletedIndex.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedObject = head;
        for (int i = 0; deletedObject != null; i++) {
            if (object == deletedObject.element
                    || object != null && object.equals(deletedObject.element)) {
                remove(i);
                return true;
            }
            deletedObject = deletedObject.next;
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

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.next = next;
            this.prev = prev;
            this.element = element;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is invalid, size: "
                    + size);
        }
    }

    private Node<T> findByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1;i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void clearLink(Node<T> clearing) {
        Node<T> prevNode = clearing.prev;
        Node<T> nextNode = clearing.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            clearing.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            clearing.next = null;
        }
        size--;
    }
}
