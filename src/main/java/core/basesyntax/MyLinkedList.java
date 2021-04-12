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
        if (index == 0) {
            head = new Node<>(null, value, head);
        } else {
            Node<T> currentNode = getSpecificNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
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
        indexCheck(index);
        Node<T> currentNode = getSpecificNode(index);
        return currentNode == null ? null : currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> currentNode = getSpecificNode(index);
        T returnValue;
        if (currentNode != null) {
            returnValue = currentNode.item;
            currentNode.item = value;
            return returnValue;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        return unlink(getSpecificNode(index), index);
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
        Node<T> currentNode;
        if (index <= size >> 1) {
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

    private T unlink(Node<T> nodeToBeRemoved, int index) {
        indexCheck(index);
        if (nodeToBeRemoved == null) {
            return null;
        }
        if (index == 0) {
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = nodeToBeRemoved.next;
            }
        } else if (nodeToBeRemoved.next != null) {
            nodeToBeRemoved.next.prev = nodeToBeRemoved.prev;
            nodeToBeRemoved.prev.next = nodeToBeRemoved.next;
        }
        size--;
        return nodeToBeRemoved.item;
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
