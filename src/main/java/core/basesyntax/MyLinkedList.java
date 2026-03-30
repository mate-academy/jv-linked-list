package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, tail);
        if (tail == null) {
            head = newNode;;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value, null, null);
        checkIndexForAdd(index);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = findNodeByIndex(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
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
        checkIndex(index);
        Node<T> newNode = findNodeByIndex(index);
        return newNode.element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = findNodeByIndex(index);
        T oldValue;
        oldValue = newNode.element;
        newNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T element = node.element;
        unlink(node);
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = head;
        for (int i = 0; i < size; i++) {
            if ((object == null && newNode.element == null)
                    || (object != null && object.equals(newNode.element))) {
                unlink(newNode);
                return true;
            }
            newNode = newNode.next;
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
        Node<T> newNode;
        if (index < size / 2) {
            newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        } else {
            newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
        }
        return newNode;
    }

    private void checkIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private void checkIndexForAdd(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private boolean unlink(Node<T> newNode) {
        if (head == tail) {
            tail = null;
            head = null;
            size--;
            return true;
        }
        if (newNode == head) {
            head = newNode.next;
            head.prev = null;
            size--;
            return true;
        } else if (newNode == tail) {
            tail = newNode.prev;
            tail.next = null;
            size--;
            return true;
        }
        newNode.prev.next = newNode.next;
        newNode.next.prev = newNode.prev;
        size--;
        return true;
    }

    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
