package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
            tail = head;
        }
        tail.next = node;
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAddIndex(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addByIndexZero(value);
        } else {
            addByIndexDefault(index, value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(findByIndex(index)).item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void checkIndexForAddIndex(int index) {
        if (index < 0 && index > size) {
            throw new IndexOutOfBoundsException("Cannot add element , because index "
                    + index + " is invalid");
        }
    }

    private void addByIndexZero(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void addByIndexDefault(int index, T value) {
        Node<T> node = findByIndex(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    private Node<T> findByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private Node<T> unlink(Node<T> node) {
        if (node == head) {
            if (head.next != null) {
                node.next.prev = null;
            }
            head = node.next;
        } else if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
