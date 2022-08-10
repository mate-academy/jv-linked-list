package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.previous, value, currentNode);
        newNode.next.previous = newNode;
        if (newNode.previous != null) {
            newNode.previous.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
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
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                removeNode(currentNode);
                size--;
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

    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
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

    private void removeNode(Node<T> currentNode) {
        if (currentNode == head) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
        } else if (currentNode == tail) {
            tail = tail.previous;
            tail.next = null;
        } else {
            currentNode.previous.next = currentNode.next;
            currentNode.next.previous = currentNode.previous;
        }
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
