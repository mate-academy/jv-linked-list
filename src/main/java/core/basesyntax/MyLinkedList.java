package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    public MyLinkedList(T element) {
        Node<T> firstNode = new Node<>(null, element, null);
        head = tail = firstNode;
        size = 1;
    }

    @Override
    public boolean add(T value) {
        Node<T> nodeToAdd = new Node<>(tail, value, null);
        if (head == null) {
            head = tail = nodeToAdd;
        } else {
            tail.next = nodeToAdd;
            tail = nodeToAdd;
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
        checkBounds(index);
        insertNode(findNode(index), value);
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkBounds(index);
        return findNode(index).storedItem;
    }

    @Override
    public T set(T value, int index) {
        checkBounds(index);
        Node<T> nodeInPlace = findNode(index);
        T elementToReplace = nodeInPlace.storedItem;
        nodeInPlace.storedItem = value;
        return elementToReplace;
    }

    @Override
    public T remove(int index) {
        checkBounds(index);
        Node<T> nodeToRemove = findNode(index);
        T previousValue = nodeToRemove.storedItem;
        unlink(nodeToRemove);
        return previousValue;
    }

    @Override
    public boolean remove(T t) {
        Node<T> holder = head;
        for (int i = 0; i < size; i++) {
            if (holder.storedItem == t || (holder.storedItem != null
                                        && holder.storedItem.equals(t))) {
                unlink(holder);
                return true;
            }
            holder = holder.next;
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

    private static class Node<T> {
        T storedItem;
        Node<T> previous;
        Node<T> next;

        Node(Node<T> previous, T element, Node<T> next) {
            storedItem = element;
            this.previous = previous;
            this.next = next;
        }
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of LinkedList bonds");
        }
    }

    private void insertNode(Node<T> targetNode, T value) {
        Node<T> nodeToInsert = new Node<>(targetNode.previous, value, targetNode);
        if (targetNode.previous == null) {
            targetNode.previous = nodeToInsert;
            head = nodeToInsert;
        } else {
            targetNode.previous.next = nodeToInsert;
            targetNode.previous = nodeToInsert;
        }
        size++;
    }

    private void unlink(Node<T> nodeToRemove) {
        if (size == 1) {
            head = tail = null;
        } else if (nodeToRemove == head) {
            nodeToRemove.next.previous = null;
            head = nodeToRemove.next;
        } else if (nodeToRemove == tail) {
            nodeToRemove.previous.next = null;
            tail = nodeToRemove.previous;
        } else {
            nodeToRemove.previous.next = nodeToRemove.next;
            nodeToRemove.next.previous = nodeToRemove.previous;
        }
        nodeToRemove.next = nodeToRemove.previous = null;
        nodeToRemove.storedItem = null;
        --size;
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }
}
