package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    public MyLinkedList() {
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        createNewNode(tail, null, value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            createNewNode(tail, null, value);
        } else {
            Node<T> current = findNodeByIndex(index);
            createNewNode(current.prev, current, value);
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        unlink(node);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = findNodeByObject(object);
        if (currentNode != null) {
            unlink(currentNode);
            return true;
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

    private void unlink(Node<T> node) {
        if (node == null) {
            throw new NullPointerException("node is null");
        }
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
        node.prev = null;
        node.next = null;
        node.item = null;
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> createNewNode(Node<T> prev, Node<T> next, T value) {
        Node<T> newNode = new Node<>(value, prev, next);
        if (prev != null) {
            prev.next = newNode;
        } else {
            head = newNode;
        }

        if (next != null) {
            next.prev = newNode;
        } else {
            tail = newNode;
        }

        return newNode;
    }

    private Node<T> findNodeByIndex(int index) {
        int count = 0;
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            while (count < index) {
                currentNode = currentNode.next;
                count++;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
        return currentNode;
    }

    private Node<T> findNodeByObject(Object object) {
        Node<T> currentNode = head;
        int count = 0;
        while (count < size()) {
            if (object == null && currentNode.item == null) {
                break;
            }
            if (object != null && currentNode.item != null && object.equals(currentNode.item)) {
                break;
            }
            currentNode = currentNode.next;
            count++;
        }
        return currentNode;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
