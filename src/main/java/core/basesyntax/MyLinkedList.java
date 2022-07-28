package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String ERROR_MESSAGE_OUT_OF_BOUNDS = "Index out of bound: ";
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException(ERROR_MESSAGE_OUT_OF_BOUNDS + index);
        }
        if (head == null) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else if (size() == index) {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        } else {
            Node<T> nodeByIndex = findNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            newNode.next.prev = newNode;
            if (index != 0) {
                newNode.prev.next = newNode;
            } else {
                head = newNode;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            tail.next = new Node<>(tail, element, null);
            tail = tail.next;
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        T oldValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        T removedValue = nodeByIndex.value;
        removeNode(nodeByIndex);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(currentNode.value, object)) {
                removeNode(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        int size = 0;
        Node<T> currentNode = head;
        while (currentNode != null) {
            size++;
            currentNode = currentNode.next;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = head;
        int currentIndex = 0;
        while (currentNode != null && index >= 0) {
            if (index == currentIndex) {
                return currentNode;
            }
            currentNode = currentNode.next;
            currentIndex++;
        }
        throw new IndexOutOfBoundsException(ERROR_MESSAGE_OUT_OF_BOUNDS + index);
    }

    private void removeNode(Node<T> currentNode) {
        if (currentNode == head) {
            if (head == tail) {
                head = null;
            } else {
                head = head.next;
            }
        } else if (currentNode == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
    }
}
