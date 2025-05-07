package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size
                    + "Index Less than 0 or Index more than size");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value, null, null);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> previousToNew = findNodeByIndex(index - 1);
            newNode.next = previousToNew.next;
            newNode.prev = previousToNew;
            previousToNew.next.prev = newNode;
            previousToNew.next = newNode;
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        T first = current.item;
        current.item = value;
        return first;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        T node = currentNode.item;
        unlink(currentNode);
        return node;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object || object != null
                    && object.equals(currentNode.item)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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
        Node<T> current;
        if (index < size / 2) {
            current = head;
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

    private void unlink(Node<T> node) {
        if (node.equals(head)) {
            head = node.next;
        } else {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
            }
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size
                    + "Index Less than 0 or Index more than size or index "
                    + "have the same value as a size");
        }
    }

    class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
