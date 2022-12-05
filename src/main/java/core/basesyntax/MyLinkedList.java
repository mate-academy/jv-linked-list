package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirstNode(value);
        } else {
            addLastNode(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == 0 && isEmpty()) {
            addFirstNode(value);
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0 && !isEmpty()) {
            newNode.next = head;
            head = newNode;
        } else if (index == size - 1) {
            Node<T> currentNode = getNode(index);
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev.next = newNode;
            currentNode.next = null;
        } else {
            Node<T> currentNode = getNode(index);
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev.next = newNode;
            currentNode.next.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T replacedValue = currentNode.value;
        currentNode.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNode(index);
        T removedValue = removedNode.value;
        unlink(removedNode);
        return removedValue;
    }

    @Override
   public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                unlink(currentNode);
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
        return head == null;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds " + index + "!");
        }
    }

    private void addFirstNode(T value) {
        Node<T> firstNode = new Node<>(null, value, null);
        head = tail = firstNode;
        size++;
    }

    private void addLastNode(T value) {
        Node<T> lastNode = new Node<>(tail, value, null);
        tail.next = lastNode;
        tail = lastNode;
        size++;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        Node<T> currentNode;
        if (index <= size / 2 + 1) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            currentNode = tail.prev;
            for (int i = size - 1; i > index + 1; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = tail = null;
        } else if (node.prev == null) {
            head = head.next;
        } else if (node.next == null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
