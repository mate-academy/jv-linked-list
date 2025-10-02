package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;
    @Override
    public void add(T value) {
        Node<T> node;
        if (this.size == 0) {
            node = new Node<T>(null, value, null);
            this.head = node;
        } else {
            node = new Node<T>(this.tail, value, null);
            this.tail.next = node;
        }
        this.tail = node;
        this.size++;
    }
    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        if (this.size == 0 || index == this.size) {
            add(value);
        } else {
            Node<T> node;
            if (index == 0) {
                node = new Node<T>(null, value, this.head);
                this.head.prev = node;
                this.head = node;
            } else {
                Node<T> temp = findNodeByIndex(index);
                node = new Node<T>(temp.prev, value, temp);
                temp.prev.next = node;
                temp.prev = node;
            }
            this.size++;
        }
    }
    @Override
    public void addAll(List<T> list) {
        if (list != null && !list.isEmpty()) {
            for (T value : list) {
                this.add(value);
            }
        }
    }
    @Override
    public T get(int index) {
        validateIndex(index);
        return findNodeByIndex(index).value;
    }
    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }
    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> node = findNodeByIndex(index);
        removeNode(node);
        T value = node.value;
        unlink(node);
        return value;
    }
    @Override
    public boolean remove(T object) {
        if (this.size > 0) {
            Node<T> node = this.head;
            for (int i = 0; i < this.size; i++) {
                if (node.value == object || node.value != null && node.value.equals(object)) {
                    removeNode(node);
                    unlink(node);
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }
    @Override
    public int size() {
        return this.size;
    }
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    private void validateIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
    private void validateIndexForAdd(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
    private Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (index >= this.size - index) {
            node = this.tail;
            for (int i = 0; i < this.size - index - 1; i++) {
                node = node.prev;
            }
        } else {
            node = this.head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }
    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            this.head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            this.tail = node.prev;
        }
        this.size--;
    }
    private void unlink(Node<T> node) {
        node.prev = null;
        node.next = null;
        node.value = null;
    }
    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
