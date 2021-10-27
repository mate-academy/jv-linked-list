package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        Node(Node<T> prev, T x, Node<T> next) {
            this.prev = prev;
            value = x;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = addedNode;
        } else {
            tail.next = addedNode;
        }
        tail = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException ("Can't ADD element by that index");
        } else if (index == size) {
            add(value);
        } else if (size == 1) {
            tail = head;
            head = new Node<>(null, value, tail);
            tail.prev = head;
            size++;
        } else if (index <= size / 2) { // size = 7 / 2 = 3/ index = 3
            Node<T> prevoiusNode = head;
            for (int i = 0; i < index; i++) {
                prevoiusNode = prevoiusNode.next;
            }
            Node<T> addedNode = new Node<>(prevoiusNode, value, prevoiusNode.next);
            prevoiusNode.next = addedNode;
        } else {
            Node<T> nextNode = tail;
            for (int i = size; i > index; i--) {
                nextNode = nextNode.prev;
            }
            Node<T> addedNode = new Node<>(nextNode.prev, value, nextNode);
            nextNode.prev = addedNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException ("Can't GET element by that index");
        }
        Node<T> nodeToGet;
        if (index <= size / 2) {
            nodeToGet = head;
            for (int i = 0; i < index; i++) {
                nodeToGet = nodeToGet.next;
            }
        } else {
            nodeToGet = tail;
            for (int i = size; i > index + 1; i--) {
                nodeToGet = nodeToGet.prev;
            }
        }
    return nodeToGet.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException ("Can't SET element by that index");
        }
        Node<T> nodeToSet;
        if (index <= size / 2) { // size = 7 / 2 = 3/ index = 3
            nodeToSet = head;
            for (int i = 0; i < index; i++) {
                nodeToSet = nodeToSet.next;
            }
        } else {
            nodeToSet = tail;
            for (int i = size; i > index + 1; i--) {
                nodeToSet = nodeToSet.prev;
            }
        }
        T oldValue = nodeToSet.value;
        nodeToSet.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size || head == null || tail == null) {
            throw new IndexOutOfBoundsException ("Can't REMOVE element by that index");
        }

        Node<T> nodeToRemove;
        Node<T> previousNode;
        Node<T> nextNode;
        if (index <= size / 2) {
            nodeToRemove = head;
            for (int i = 0; i < index; i++) {
                nodeToRemove = nodeToRemove.next;
            }
        } else {
            nodeToRemove = tail;
            for (int i = size; i > index + 1; i--) {
                nodeToRemove = nodeToRemove.prev;
            }
        }
        previousNode = nodeToRemove.prev;
        nextNode = nodeToRemove.next;
        if (nodeToRemove.prev != null) {
            nodeToRemove.prev = nodeToRemove.next;
        }
        if (nextNode != null) {
            nodeToRemove.next = previousNode;
        }
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> nodeToRemove;
        Node<T> previousNode;
        Node<T> nextNode;
            nodeToRemove = head;
            for (int i = 0; i < size; i++) {
                nodeToRemove = nodeToRemove.next;
                if (nodeToRemove.value.equals(object)) {
                    previousNode = nodeToRemove.prev;
                    nextNode = nodeToRemove.next;
                    previousNode.next = nodeToRemove.next;
                    nextNode.prev = nodeToRemove.prev;
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
}
