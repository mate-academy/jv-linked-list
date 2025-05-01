package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
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
        if (index != size()) {
            checkIndexForAddMethod(index);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty() && index == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.right = head;
            head.left = newNode;
            head = newNode;
        } else if (index == size() - 1) {
            newNode.left = tail.left;
            newNode.right = tail;
            tail.left.right = newNode;
            tail.left = newNode;
        } else if (index == size()) {
            tail.right = newNode;
            newNode.left = tail;
            tail = tail.right;
        } else {
            Node<T> tempNode = findByIndex(index);
            tempNode.left.right = newNode;
            newNode.right = tempNode;
            newNode.left = tempNode.left;
            tempNode.left = newNode;
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
            if (size > 1) {
                head = temp.right;
                head.left = null;
            } else {
                head = tail = null;
            }
        } else if (temp == tail) {
            tail = tail.left;
            temp.left.right = null;
        } else {
            temp.left.right = temp.right;
            temp.right.left = temp.left;
        }
        unlink(temp);
        size--;
        return temp.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if ((temp.data != null && temp.data.equals(object))
                    || (temp.data == null && null == object)) {
                remove(i);
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
        return size == 0;
    }

    private void checkIndexForAddMethod(int index) {
        if (index < 0 || (isEmpty() && index > 0) || index > size) {
            throw new IndexOutOfBoundsException("Element not found in the list");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Element not found in the list");
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> node;
        if (index <= (size() >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.right;
            }
        } else {
            node = tail;
            for (int i = size() - 1; i > index; i--) {
                node = node.left;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        node.left = node.right = null;
    }

    private static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(Node<T> left, T item, Node<T> right) {
            this.left = left;
            this.data = item;
            this.right = right;
        }
    }
}
