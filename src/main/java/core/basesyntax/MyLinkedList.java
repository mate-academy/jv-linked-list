package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return true;
        }
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        checkIndex(index);
        Node<T> newNode = findNode(index);
        Node<T> add = new Node<>(newNode.prev, value, newNode);
        newNode.prev.next = add;
        newNode.prev = add;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> newNode = findNode(index);
        return newNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = findNode(index);
        T result = newNode.value;
        newNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> newNode = findNode(index);
        T removeValue = newNode.value;
        unlink(newNode);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = head;
        for (int i = 0; i < size; i++) {
            if (object == newNode.value || object != null && object.equals(newNode.value)) {
                unlink(newNode);
                return true;
            }
            newNode = newNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not correct index " + index);
        }
    }

    private Node<T> findNode(int index) {
        Node<T> newNode;
        if (index < size) {
            newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        } else {
            newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
            return newNode;
        }
    }

    private T unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        T result = node.value;
        size--;
        return result;
    }

    class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
