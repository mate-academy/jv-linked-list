package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> prev = tail;
        Node<T> newNode = new Node<>(prev, value, null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index can't be less"
                    + " then zero or greater then size");
        }
        if (index == 0) {
            head = new Node<>(null, value, head);
            if (size == 0) {
                tail = head;
            }
            size++;
            return;
        }
        if (index == size) {
            tail = new Node<>(tail, value, null);
            tail.prev.next = tail;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        newNode.prev = getNodeByIndex(index - 1);
        newNode.next = newNode.prev.next;
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldElement = getNodeByIndex(index).value;
        Node<T> node = getNodeByIndex(index);
        node.value = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        T deletedEllement;
        checkIndex(index);
        if (index == 0) {
            deletedEllement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            deletedEllement = prev.next.value;
            prev.next = prev.next.next;
            if (index == size - 1) {
                tail = prev;
            }
        }
        size--;
        return deletedEllement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (isEqual(current.value, object)) {
                remove(i);
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
        return head == null;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
            if (temp == null) {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }
        }
        return temp;
    }

    private boolean isEqual(T value1, T value2) {
        if (value1 == null) {
            return value2 == null;
        }
        return value1.equals(value2);
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be less then zero");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index can't be greater then or equal to size");
        }
    }
}
