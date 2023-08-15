package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> lastNode = tail;
        Node<T> node = new Node<>(lastNode, value, null);
        tail = node;

        if (lastNode == null) {
            head = node;
        } else {
            lastNode.next = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddedPosition(index);
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            add(value);
            return;
        }

        Node<T> node = findNodeByIndex(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    public void addFirst(T value) {
        Node<T> firstNode = head;
        Node<T> newNode = new Node<>(null, value, firstNode);
        head = newNode;

        if (firstNode == null) {
            tail = newNode;
        } else {
            firstNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        return unlink(findNodeByValue(object));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private void checkAddedPosition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("You can't add value in position " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        int middle = size / 2;
        return index >= middle ? findFromEnd(index) : findFromStart(index);
    }

    private Node<T> findNodeByValue(T value) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == value || node.value != null && node.value.equals(value)) {
                break;
            }
            node = node.next;
        }
        return node;
    }

    private Node<T> findFromStart(int index) {
        int position = 0;
        Node<T> node = head;
        while (index != position) {
            node = node.next;
            position++;
        }
        return node;
    }

    private Node<T> findFromEnd(int index) {
        int positon = size - 1;
        Node<T> node = tail;
        while (index != positon) {
            node = node.prev;
            positon--;
        }
        return node;
    }

    private boolean unlink(Node<T> node) {
        if (node == null) {
            return false;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
        return true;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
