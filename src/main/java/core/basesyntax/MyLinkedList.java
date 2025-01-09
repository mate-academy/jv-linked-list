package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value,Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            tail = head = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new java.lang.IndexOutOfBoundsException("Index out of bounds " + index);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head == null) {
                head = tail = newNode;
            } else {
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size()) {
            Node<T> newNode = new Node<>(tail, value, null);
            if (tail == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        } else {
            int counter = 0;
            Node<T> newNode = head;
            while (counter != index) {
                newNode = newNode.next;
                counter++;
            }
            Node<T> currentNode = new Node<>(newNode.prev, value, newNode);
            newNode.prev.next = currentNode;
            newNode.prev = currentNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("List is null");
        }
        if (list.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Node<T> newNode = new Node<>(tail, list.get(i), null);
            tail.next = newNode;
            tail = newNode;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new java.lang.IndexOutOfBoundsException("Index is less than 0");
        }
        int counter = 0;
        Node<T> node = head;
        while (node != null && counter != index) {
            counter++;
            node = node.next;
        }
        if (node == null) {
            throw new java.lang.IndexOutOfBoundsException("Index doesnt exist in the list");
        }
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size()) {
            throw new java.lang.IndexOutOfBoundsException("Index is less than 0 or more than size");
        }
        if (value == null) {
            throw new java.lang.IndexOutOfBoundsException("Value is null");
        }
        int counter = 0;
        Node<T> node = head;
        while (counter != index) {
            counter++;
            node = node.next;
        }
        if (node == null) {
            throw new java.lang.IndexOutOfBoundsException("Index doesnt exist in the list");
        }
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0) {
            throw new java.lang.IndexOutOfBoundsException("Index is less than 0");
        }
        int counter = 0;
        Node<T> node = head;
        while (node != null && counter != index) {
            counter++;
            node = node.next;
        }
        if (node == null) {
            throw new java.lang.IndexOutOfBoundsException("Index doesnt exist in the list");
        }
        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = head;
        while (newNode != null && !Objects.equals(newNode.value, object)) {
            newNode = newNode.next;
        }
        if (newNode == null) {
            return false;
        }
        if (newNode == head) {
            head = newNode.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (newNode == tail) {
            tail = newNode.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            newNode.next.prev = newNode.prev;
            newNode.prev.next = newNode.next;
        }
        return true;
    }

    @Override
    public int size() {
        int counter = 0;
        Node<T> currentNode = head;
        while (currentNode != null) {
            counter++;
            currentNode = currentNode.next;
        }
        return counter;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
