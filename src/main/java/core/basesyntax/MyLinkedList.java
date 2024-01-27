package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, head);

        if (head == null) {
            tail = newNode;
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexCheck(index);
        Node<T> currentNode = findByIndex(index);
        Node<T> newNode = new Node<>(value, currentNode.prev, currentNode);
        if (index == 0) {
            head = newNode;
        } else {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> currentNode = findByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> currentNode = findByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeObject = head;
        while (removeObject != null) {
            if (removeObject.value == object
                    || object != null && object.equals(removeObject.value)) {
                unlink(removeObject);
                return true;
            }
            removeObject = removeObject.next;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds " + index);
        }
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode == head) {
            head = currentNode.next;
            if (size > 1) {
                head.prev = null;
            }
        } else if (currentNode == tail) {
            tail = currentNode.prev;
            tail.next = null;
        } else {
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
        }
        size--;
    }

    private Node<T> findByIndex(int index) {
        int currentIndex = 0;
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentIndex == index) {
                return currentNode;
            }
            currentNode = currentNode.next;
            currentIndex++;
        }
        return head;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
