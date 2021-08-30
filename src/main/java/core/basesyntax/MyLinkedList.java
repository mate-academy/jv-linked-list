package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
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
        if (index == size) {
            add(value);
            return;
        }
        Node<T> indexNode = getNode(index);
        if (indexNode == head) {
            Node<T> newNode = new Node<>(null, value, indexNode);
            indexNode.prev = newNode;
            head = newNode;
        } else {
            Node<T> newNode = new Node<>(indexNode.prev, value, indexNode);
            indexNode.prev.next = newNode;
            indexNode.prev = newNode;
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
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> indexNode = getNode(index);
        T oldValue = indexNode.item;
        indexNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> indexNode = getNode(index);
        size--;
        if (head == indexNode && tail == indexNode) {
            head = null;
            tail = null;
            return indexNode.item;
        }
        if (head == indexNode) {
            indexNode.next.prev = null;
            head = indexNode.next;
            return indexNode.item;
        }
        if (tail == indexNode) {
            indexNode.prev.next = null;
            tail = indexNode.prev;
            return indexNode.item;
        }
        indexNode.prev.next = indexNode.next;
        indexNode.next.prev = indexNode.prev;
        return indexNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == object
                    || (node.item != null && node.item.equals(object))) {
                remove(i);
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

    private Node<T> getNode(int index) {
        Node<T> result;
        if (index < (size >> 1)) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
