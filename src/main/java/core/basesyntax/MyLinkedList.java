package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node head;
    private Node tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail != null) {
            newNode.last.next = newNode;
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
            head.last = newNode;
            head = newNode;
        } else {
            Node currentNode = findNode(index);
            Node newNode = new Node(currentNode.last, value, currentNode);
            currentNode.last.next = newNode;
            currentNode.last = newNode;
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
        Node current = findNode(index);
        return (T) current.element;
    }

    @Override
    public T set(T value, int index) {
        Node current = findNode(index);
        T oldValue = (T) current.element;
        current.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node current = findNode(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (findNode(i).element == object
                    || (findNode(i).element != null && findNode(i).element.equals(object))) {
                remove(i);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
    }

    private class Node<T> {
        private T element;
        private Node next;
        private Node last;

        public Node(Node last, T element, Node next) {
            this.last = last;
            this.element = element;
            this.next = next;
        }
    }

    private Node findNode(int index) {
        checkIndex(index);
        Node currentNode;
        if (index < (size / 2)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.last;
            }
        }
        return currentNode;
    }

    private T unlink(Node currentNode) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (currentNode.last == null) {
            head = currentNode.next;
            head.last = null;
        } else if (currentNode.next == null) {
            tail = currentNode.last;
            tail.next = null;
        } else {
            currentNode.last.next = currentNode.next;
            currentNode.next.last = currentNode.last;
        }
        size--;
        return (T) currentNode.element;
    }
}
