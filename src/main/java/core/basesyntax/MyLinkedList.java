package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.right = newNode;
            newNode.left = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size != index) {
            checkIndex(index);
        }
        Node<T> newNode = new Node<>(value);
        size++;
        if (this.isEmpty() && index == 0) {
            head = tail = newNode;
            return;
        }
        if (index == 0) {
            newNode.right = head;
            if (head != null) {
                head.left = newNode;
            }
            head = newNode;
            return;
        }
        if (index == size - 1) {
            tail.right = newNode;
            newNode.left = tail;
            tail = newNode;
            return;
        }
        Node<T> tempNode = findByIndex(index);
        tempNode.left.right = newNode;
        newNode.right = tempNode;
        newNode.left = tempNode.left;
        tempNode.left = newNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            this.add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> temp = findByIndex(index);
        return temp.data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> temp = findByIndex(index);
        T old = temp.data;
        temp.data = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> temp = findByIndex(index);
        if (temp == head) {
            if (this.size > 1) {
                head = temp.right;
                temp.right = null;
                head.left = null;
            } else {
                head = null;
                tail = null;
            }
        } else if (temp == tail) {
            temp.left.right = null;
        } else {
            temp.left.right = temp.right;
            temp.right.left = temp.left;
        }
        size--;
        return temp.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if ((temp.data != null && temp.data.equals(object))
                    || (temp.data == null && null == object)) {
                this.remove(i);
                return true;
            }
            temp = temp.right;
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

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1 || (this.isEmpty() && index > 0)) {
            throw new IndexOutOfBoundsException("Element not found in the list");
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.right;
        }
        return node;
    }

    private static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
            left = right = null;
        }
    }
}
