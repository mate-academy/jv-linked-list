package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) {
            tail = head = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't add object to chosen index");
        }
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(null, value, null);

        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> curentNode = findByIndex(index);
            newNode.prev = curentNode.prev;
            newNode.next = curentNode;
            curentNode.prev.next = newNode;
            curentNode.prev = newNode;
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
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> curentNode = findByIndex(index);
        T oldValue = curentNode.item;
        curentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findByIndex(index);
        T oldValue = currentNode.item;
        unlinkNode(currentNode);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode;
        if (object == null) {
            for (currentNode = this.head; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.item == object) {
                    unlinkNode(currentNode);
                    return true;
                }
            }
        } else {
            for (currentNode = this.head; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.item)) {
                    unlinkNode(currentNode);
                    return true;
                }
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

    private Node<T> findByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Object on selected index is null!");
        }
        Node<T> currentNode = this.head;
        if (index == 0) {
            return this.head;
        }
        if (index == size - 1) {
            return this.tail;
        }
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlinkNode(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;

        if (prevNode == null) {
            this.head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            this.tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }

        node.item = null;
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }
}
