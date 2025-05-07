package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            addFirstElement(value);
            return;
        }

        head.next = new Node(value, head, null);
        head.next.previous = head;
        head = head.next;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size " + size);
        }

        if (index == 0) {
            addTail(value);
            return;
        }

        if (index == size) {
            add(value);
            return;
        }

        addMiddle(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNode(index).value;
        getNode(index).value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        T removedValue = getNode(index).value;
        removeNode(getNode(index));

        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        try {
            removeNode(getNode(object));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private T removeNode(Node<T> node) {
        if (tail == node && head == node) {
            tail = null;
            head = null;
        } else if (tail == node) {
            tail = node.next;
        } else if (head == node) {
            head = node.previous;
        } else {
            node.next.previous = node.previous;
            node.previous.next = node.next;
        }

        size--;

        return node.value;
    }

    private Node<T> getNode(T value) {
        Node<T> currentNode = tail;
        for (int i = 0; i <= size; i++) {
            if (currentNode == null) {
                break;
            }

            if (equals(currentNode.value, value)) {
                return currentNode;
            }

            currentNode = currentNode.next;
        }

        throw new NoSuchElementException("No " + value + " element in list");
    }

    private Node<T> getNode(int index) {
        if (tail == null) {
            throw new IndexOutOfBoundsException("Invalid index " + index
                    + ", because list is empty.");
        }

        checkIndex(index);

        if (index == size - 1) {
            return head;
        }

        Node<T> currentNode = tail;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size " + size);
        }
    }

    private Node<T> addTail(T element) {
        if (tail == null) {
            return addFirstElement(element);
        }

        Node<T> newNode = new Node<>(element);
        newNode.next = tail;
        tail = newNode;
        newNode.next.previous = newNode;
        size++;

        return newNode;
    }

    private Node<T> addMiddle(T element, int index) {
        Node<T> newNode = new Node<>(element);
        newNode.next = getNode(index);
        newNode.previous = newNode.next.previous;
        newNode.next.previous = newNode;
        newNode.previous.next = newNode;
        size++;

        return newNode;
    }

    private Node<T> addFirstElement(T element) {
        Node<T> newNode = new Node<>(element);
        tail = newNode;
        head = newNode;
        size++;

        return newNode;
    }

    private boolean equals(T firstObject, T secondObject) {
        return firstObject == secondObject
                || (firstObject != null && firstObject.equals(secondObject));
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }
}
