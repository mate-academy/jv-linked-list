package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index is out of range: " + index);
        }

        if (index == 0) {
            addFirst(value);
        } else if (index == size()) {
            addLast(value);
        } else {
            Node<T> nodeAtIndex = getNodeAtIndex(index - 1); // Get the node at index - 1
            Node<T> newNode = new Node<>(nodeAtIndex, value, nodeAtIndex.next);
            nodeAtIndex.next.prev = newNode;
            nodeAtIndex.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        isIndexOutOfArray(index);
        Node<T> current = head;
        int currentIndex = 0;

        while (current != null && currentIndex < index) {
            current = current.next;
            currentIndex++;
        }
        if (current != null && currentIndex == index) {
            return current.value;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        isIndexOutOfArray(index);
        Node<T> nodeAtIndex = getNodeAtIndex(index);

        if (nodeAtIndex != null) {
            T oldValue = nodeAtIndex.value;
            nodeAtIndex.value = value;
            return oldValue;
        }

        return null;
    }

    @Override
    public T remove(int index) {
        isIndexValid(index);
        Node<T> nodeToRemove = getNodeAtIndex(index);
        T valueToRemove = nodeToRemove.value;
        removeNode(nodeToRemove);
        size--;
        return valueToRemove;
    }

    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (Objects.equals(object, current.value)) {
                removeNode(current);
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size++;
    }

    // Helper method to add an element at the end of the list
    private void addLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        isIndexValid(index);
        Node<T> current;
        int currentIndex;

        if (index > size() / 2) {
            current = tail;
            currentIndex = size() - 1;
            while (currentIndex > index) {
                current = current.prev;
                currentIndex--;
            }
        } else {
            current = head;
            currentIndex = 0;
            while (currentIndex < index) {
                current = current.next;
                currentIndex++;
            }
        }
        return current;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void isIndexOutOfArray(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index is out of range: "
                    + index + " for size: " + size());
        }
    }

    private void isIndexValid(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
