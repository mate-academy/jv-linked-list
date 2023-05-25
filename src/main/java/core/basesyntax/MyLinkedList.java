package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

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
        if (size == index) {
            add(value);
        } else if (index == 0) {
            addNewHead(value);
        } else {
            addBefore(value, findNode(index));
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
        Node<T> result = findNode(index);
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNode(index);
        T result;
        result = currentNode.value;
        currentNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        indexExistCheck(index);
        Node<T> result = findNode(index);
        removeNode(result);
        return result.value;
    }

    public boolean remove(T object) {
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if (object == nodeToRemove.value || object != null
                    && object.equals(nodeToRemove.value)) {
                removeNode(nodeToRemove);
                return true;
            }
            nodeToRemove = nodeToRemove.next;
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

    private Node<T> findNode(int index) {
        indexExistCheck(index);
        Node<T> currentNode = head;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size; i > index + 1; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void indexExistCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index do not exist");
        }
    }

    private void removeNode(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
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

    private void addBefore(T value, Node<T> node) {
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    private void addNewHead(T value) {
        Node<T> node = head;
        Node<T> newNode = new Node<>(null, value, node);
        node.prev = newNode;
        head = newNode;
        size++;
    }

    static class Node<T> {
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
