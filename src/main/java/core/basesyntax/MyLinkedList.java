package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int VALUE_NOT_FOUND = -1;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            add(value);
        } else {
            Node<T> currentAtIndex = getObjByIndex(index);
            insert(value, currentAtIndex);
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
        return getObjByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getObjByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getObjByIndex(index);
        size--;
        return unlink(current);
    }

    @Override
    public boolean remove(T value) {
        Node<T> current = getNode(value);
        if (current == null) {
            return false;
        }
        unlink(current);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addFirst(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (size == 0) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void insert(T value, Node<T> afterNewNode) {
        Node<T> beforeNewNode = afterNewNode.prev;
        Node<T> newNode = new Node<>(beforeNewNode, value, afterNewNode);
        beforeNewNode.next = newNode;
        afterNewNode.prev = newNode;
        size++;
    }

    private Node<T> getObjByIndex(int index) {
        checkElementIndex(index);
        Node<T> current = head;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> getNode(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == value || value != null && value.equals(current.value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private T unlink(Node<T> node) {
        Node<T> beforeNode = node.prev;
        Node<T> afterNode = node.next;
        if (beforeNode == null) {
            head = afterNode;
        } else {
            beforeNode.next = afterNode;
            node.prev = null;
        }

        if (afterNode == null) {
            tail = beforeNode;
        } else {
            afterNode.prev = beforeNode;
            node.next = null;
        }
        return node.value;
    }

    private boolean isElementIndex(int index) {
        return (index >= 0 && index < size);
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
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
