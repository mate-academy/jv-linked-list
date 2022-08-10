package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index "
                    + index + " passed to method is invalid");
        }
        if (size == index) {
            add(value);
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev = newNode;
            if (index == 0) {
                head = newNode;
            } else {
                newNode.prev.next = newNode;
            }
            size++;
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
        checkIndexValidation(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexValidation(index);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexValidation(index);
        Node<T> node = findNodeByIndex(index);
        removeNode(node);
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (x.item == object || object != null && object.equals(x.item)) {
                removeNode(x);
                size--;
                return true;
            }
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

    private void checkIndexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index "
                    + index + " passed to method is invalid");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index > size / 2) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void removeNode(Node<T> node) {
        if (head == tail) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head.next.prev = null;
            head = head.next;
        } else if (node.next == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
