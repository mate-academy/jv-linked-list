package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            if (head.next == null) {
                tail = head;
            }
        } else if (index == size) {
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> previous = findNodeByIndex(index - 1);
            newNode.next = previous.next;
            previous.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        Node<T> node = findNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        T deletedElement;
        if (index == 0) {
            deletedElement = head.value;
            removeHead();
        } else {
            Node<T> previousNode = findNodeByIndex(index - 1);
            deletedElement = previousNode.next.value;
            previousNode.next = previousNode.next.next;
            if (index == size - 1) {
                tail = previousNode;
            }
        }
        size--;
        return deletedElement;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) == object || object != null && object.equals(get(i))) {
                remove(i);
                return true;
            }
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

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index, size);
        if (index == size - 1) {
            return tail;
        } else {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
    }

    private void removeHead() {
        head = head.next;
        if (head == null) {
            tail = null;
        }
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound for index: " + index);
        }
    }
}
