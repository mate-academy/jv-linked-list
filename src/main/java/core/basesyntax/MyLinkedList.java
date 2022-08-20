package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
            size++;
            return;
        }
        newNode.previous = tail;
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addAsHead(value);
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node newNode = new Node(value);
        Node currentNode = getNodeByIndex(index);
        insertBefore(currentNode, newNode);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index == 0) {
            return head.value;
        }
        if (index == size - 1) {
            return tail.value;
        }
        return (T) getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T replacedValue;
        if (index == 0) {
            replacedValue = head.value;
            head.value = value;
            return replacedValue;
        }
        if (index == size - 1 && index > 0) {
            replacedValue = tail.value;
            tail.value = value;
            return replacedValue;
        }
        checkIndex(index);
        Node current = getNodeByIndex(index);
        replacedValue = (T) current.value;
        current.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        Node removedNode;
        if (size == 1 && index == 0) {
            removedNode = head;
            head = tail = null;
            size--;
            return (T) removedNode.value;
        }
        if (index == 0) {
            removedNode = head;
            head = removedNode.next;
            head.previous = null;
            size--;
            return (T) removedNode.value;
        }
        checkIndex(index);
        if (index == size - 1) {
            removedNode = tail;
            tail = removedNode.previous;
            tail.next = null;
            size--;
            return (T) removedNode.value;
        }
        removedNode = getNodeByIndex(index);
        removedNode.previous.next = removedNode.next;
        removedNode.next.previous = removedNode.previous;
        size--;
        return (T) removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || currentNode.value.equals(object) && currentNode.value != null) {
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

    private void addAsHead(T value) {
        if (size == 0) {
            add(value);
            return;
        }
        Node newNode = new Node(value);
        newNode.next = head;
        newNode.next.previous = newNode;
        head = newNode;
        size++;
    }

    private void insertBefore(Node currentNode, Node newNode) {
        currentNode.previous.next = newNode;
        newNode.previous = currentNode.previous;
        currentNode.previous = newNode;
        newNode.next = currentNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getNodeByIndex(int index) {
        Node currentNode;
        if (size / 2 > index) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
            return currentNode;
        }
    }

    private static class Node<T> {
        private T value;
        private Node next;
        private Node previous;

        public Node(T value) {
            this.value = value;
        }
    }
}
