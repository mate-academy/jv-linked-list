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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldElement = node.value;
        node.value = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeByIndex(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (isEqual(current.value, object)) {
                unlink(current);
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
        Node<T> temp;
        if (index < size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        if (temp == null) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
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

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
