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

        Node(T value) {
            this.value = value;
        }

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
        if (list != null && !list.isEmpty()) {
            for (T val : list) {
                add(val);
            }
            return true;
        }
        return false;
    }

    @Override
    public T get(int index) {
        indexInRange(index);
        Node<T> currentNode = getCurrentNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        indexInRange(index);
        Node<T> currentNode = getCurrentNode(index);
        T prevValue = currentNode.value;
        currentNode.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        indexInRange(index);
        Node<T> currentNode = getCurrentNode(index);
        T deletedNode = currentNode.value;
        deleteNode(currentNode);
        return deletedNode;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        int counter = 0;
        while (counter < size) {
            if (object != null && object.equals(currentNode.value) || object == currentNode.value) {
                deleteNode(currentNode);
                return true;
            }
            currentNode = currentNode.next;
            counter++;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private void addToTail(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.prev = tail;
        tail = newNode;
        if (newNode.prev == null) {
            head = newNode;
        } else {
            (newNode.prev).next = newNode;
        }
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

    private void indexInRange(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
    }

    private boolean checkIndex(int index) {
        return index < size && index >= 0;
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
