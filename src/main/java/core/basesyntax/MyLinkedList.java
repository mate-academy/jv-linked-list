package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> header;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (size == 0) {
            header = node;
            tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            header.prev = newNode;
            newNode.next = header;
            header = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev = newNode;
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).data;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        currentNode.data = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T data = null;
        if (index == 0) {
            data = header.data;
            header = header.next;
        } else if (index == size) {
            data = tail.data;
            tail = node.prev;
        } else {
            data = node.data;
            node.prev.next = node.next;
            node.prev = node.next;
            node.prev = null;
            node.next = null;
        }
        size--;
        return data;
    }

    @Override
    public T remove(T t) {
        return checkValue(t) ? remove(getIndexNodeByValue(t)) : null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = header;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private int getIndexNodeByValue(T value) {
        Node<T> node = header;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (node.data != null && node.data.equals(value) || node.data == value) {
                index = i;
                break;
            }
            node = node.next;
        }
        return index;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size && index != 0) {
            throw new IndexOutOfBoundsException("Index - " + index + " is outside ");
        }
    }

    private boolean checkValue(T value) {
        Node<T> node = header;
        for (int i = 0; i < size; i++) {
            if (node.data != null && node.data.equals(value) || node.data == value) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }
}
