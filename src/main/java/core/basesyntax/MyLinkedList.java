package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        if (head == null) {
            Node<T> firstNode = new Node<>(null, value, null);
            head = firstNode;
            tail = firstNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexCheck(index);
        Node<T> currentNode = getSpecificNode(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (currentNode != null) {
            if (index == 0) {
                newNode.next = currentNode;
                head = newNode;
            } else {
                currentNode.prev.next = newNode;
                newNode.next = currentNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T currentNode : list) {
            add(currentNode);
        }
        return true;
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = getSpecificNode(index);
        if (currentNode != null) {
            return currentNode.item;
        }
        throw new IndexOutOfBoundsException("For index: " + index + " Size: " + size);
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getSpecificNode(index);
        T returnValue;
        if (currentNode != null) {
            returnValue = currentNode.item;
            currentNode.item = value;
            return returnValue;
        }
        throw new IndexOutOfBoundsException("For index: " + index + " Size: " + size);
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> currentNode = getSpecificNode(index);
        T returnValue = null;
        if (currentNode != null) {
            if (index == 0) {
                if (size == 1) {
                    head = null;
                    tail = null;
                    size--;
                    return currentNode.item;
                }
                returnValue = currentNode.item;
                head = currentNode.next;
                size--;
                return returnValue;
            }
            returnValue = currentNode.item;
            if (currentNode.next != null) {
                currentNode.next.prev = currentNode.prev;
                currentNode.prev.next = currentNode.next;
            }
            size--;
        }
        return returnValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        int i = 0;
        while (currentNode != null) {
            if (currentNode.item == object || currentNode.item.equals(object)) {
                remove(i);
                return true;
            }
            i++;
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

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("For index: " + index + " Size: " + size);
        }
    }

    private Node<T> getSpecificNode(int index) {
        Node<T> currentNode = head;
        int i = 0;
        while (currentNode != null) {
            if (i == index) {
                return currentNode;
            }
            i++;
            currentNode = currentNode.next;
        }
        return null;
    }

    static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
