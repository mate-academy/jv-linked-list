package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    int size;

    Node<T> head;
    Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstHead(value);
        } else {
            addNewTail(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index do not exist");
        }
        if (index == 0 && size == 0) {
            addFirstHead(value);
        } else if (index == 0) {
            addNewHead(value);
        } else if (index == size) {
            addNewTail(value);
        } else {
            addByIndex(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            addNewTail(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index + 1 > size) {
            throw new IndexOutOfBoundsException("Index do not exist");
        }
        Node<T> result = head;
        int counter = 0;
        while (counter != index) {
            result = result.next;
            counter++;
        }
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index do not exist");
        }

        Node<T> result = head;
        if (index == 0) {
            Node<T> node = new Node<>(null, value, result.next);
            result.next.prev = node;
            head = node;
            return result.value;
        } else if (index == size - 1) {
            result = tail;
            Node<T> node = new Node<>(result.prev, value, null);
            result.prev.next = node;
            tail = node;
            return result.value;
        } else {
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            Node<T> node = new Node<>(result.prev, value, result.next);
            result.next.prev = node;
            result.prev.next = node;
            return result.value;
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index do not exist");
        }
        Node<T> result = head;
        if (size == 1) { // delete head if only head exist
            Node<T> node = new Node<>(null, null, null);
            head = node;
            tail = node;
            size--;
            return result.value;
        }
        if (index == 0) { // delete head
            result = head.next;
            Node<T> node = new Node<>(null, result.value, result.next);
            head = node;
            result.next.prev = node;
            size--;
            return result.prev.value;
        }
        if (index == size - 1) { //delete tail
            result = tail;
            result.prev.next = null;
            tail = result.prev;
            size--;
            return result.value;
        } else { // delete middle
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            result.next.prev = result.prev;
            result.prev.next = result.next;
            size--;
            return result.value;
        }
    }

    @Override
    public boolean remove(T object) {
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

    private void addFirstHead(T value) {
        Node<T> node = new Node<>(null, value, null);
        head = node;
        tail = node;
        size++;
    }

    private void addNewTail(T value) {
        Node<T> node2 = new Node<>(tail, value, null);
        tail.next = node2;
        tail = node2;
        size++;
    }

    private void addByIndex(T value, int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    private void addNewHead(T value) {
        Node<T> node = head;
        Node<T> node1 = new Node<>(null, value, node);
        node.prev = node1;
        head = node1;
        size++;
    }

    static class Node<T> {
        Node<T> prev;
        T value;
        Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
