package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index);
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> previous = getNodeByIndex(index - 1);
            newNode.next = previous.next;
            previous.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        return unlink(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        int index = 0;
        do {
            if (object == current.element
                    || (current.element != null
                    && current.element.equals(object))) {
                remove(index);
                return true;
            }
            index++;
            current = current.next;
        } while (current != null);
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

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

    private Node<T> findPreviousNode(Node<T> node) {
        Node<T> current = head;
        while (current != null && current.next != node) {
            current = current.next;
        }
        return current;
    }

    private T unlink(Node<T> nodeToRemove) {
        T removedElement = nodeToRemove.element;
        if (nodeToRemove == head) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> previous = findPreviousNode(nodeToRemove);
            previous.next = nodeToRemove.next;
            if (nodeToRemove == tail) {
                tail = previous;
            }
        }
        size--;
        return removedElement;
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Unable to set index: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Unable to set index: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
