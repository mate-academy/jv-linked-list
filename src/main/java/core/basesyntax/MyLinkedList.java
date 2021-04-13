package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        addToTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
        if (index == size) {
            addToTail(value);
        } else {
            addByIndex(value, index);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getCurrentNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        T prevValue = currentNode.value;
        currentNode.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        T deletedNode = currentNode.value;
        deleteNode(currentNode);
        return deletedNode;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object != null && object.equals(currentNode.value) || object == currentNode.value) {
                deleteNode(currentNode);
                return true;
            }
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

    private void addToTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private Node<T> getCurrentNode(int index) {
        Node<T> currentNode;
        int counter;
        if (index <= (size / 2)) {
            currentNode = head;
            counter = 0;
            while (counter != index) {
                currentNode = currentNode.next;
                counter++;
            }
        } else {
            currentNode = tail;
            counter = size - 1;
            while (counter != index) {
                currentNode = currentNode.prev;
                counter--;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (!(index < size && index >= 0)) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
    }

    private void addByIndex(T value, int index) {
        Node<T> currentNode = getCurrentNode(index);
        Node<T> prevNode = currentNode.prev;
        Node<T> addedNode = new Node<>(prevNode, value, currentNode);
        currentNode.prev = addedNode;
        if (prevNode == null) {
            head = addedNode;
        } else {
            prevNode.next = addedNode;
        }
        size++;
    }

    private void deleteNode(Node<T> currentNode) {
        Node<T> nextNode = currentNode.next;
        Node<T> prevNode = currentNode.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            currentNode.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            currentNode.next = null;
        }
        currentNode.value = null;
        size--;
    }
}
