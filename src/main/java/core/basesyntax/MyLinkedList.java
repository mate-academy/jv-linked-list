package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        }
        if (head == null) {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node newNode = new Node(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node currentNode = findNode(index);
            Node newNode = new Node(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node currentNode = findNode(index);
        return (T) currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        Node currentNode = findNode(index);
        T oldValue = (T) currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node currentNode = findNode(index);
        return unlinkNode(currentNode);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == node.element || object != null
                    && object.equals(node.element)) {
                unlinkNode(node);
                return true;
            }
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

    private class Node<T> {
        private T element;
        private Node next;
        private Node prev;

        public Node(Node prev, T element, Node next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node findNode(int index) {
        checkIndex(index);
        Node currentNode;
        if (index < (size >> 1)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
    }

    private T unlinkNode(Node currentNode) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (currentNode.prev == null) {
            head = currentNode.next;
            head.prev = null;
        } else if (currentNode.next == null) {
            tail = currentNode.prev;
            tail.next = null;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return (T) currentNode.element;
    }
}
