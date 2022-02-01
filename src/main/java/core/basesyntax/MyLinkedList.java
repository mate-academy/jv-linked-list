package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node<T> getNodeByIndex(int index) {
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    private Node<T> unlink(int index) {
        Node<T> unlinkNode = getNodeByIndex(index);
        if (unlinkNode.next == null && unlinkNode.previous == null) {
            head = tail = null;
        } else if (unlinkNode.previous == null) {
            head = head.next;
            head.previous = null;
        } else if (unlinkNode.next == null) {
            tail = tail.previous;
            tail.next = null;
        } else {
            unlinkNode.previous.next = unlinkNode.next;
            unlinkNode.next.previous = unlinkNode.previous;
        }
        size--;
        return unlinkNode;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = tail = new Node<>(value, null, null);
        } else {
            Node<T> newNode = new Node<>(value, null, tail);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value, null, null);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = head.previous;
            size++;
        } else {
            checkIndex(index);
            Node<T> currentNode = getNodeByIndex(index);
            currentNode.previous.next = newNode;
            newNode.previous = currentNode.previous;
            newNode.next = currentNode;
            currentNode.previous = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T valueToReturn = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return valueToReturn;

    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(index).value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object || currentNode.value != null
                    && currentNode.value.equals(object)) {
                remove(i);
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
}
