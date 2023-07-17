package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            tail = head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addHeadToList(newNode);
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            newNode.prev = prev;
            newNode.next = prev.next;
            prev.next.prev = newNode;
            prev.next = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexInBounds(index);
        T removedElement;
        if (index == 0) {
            removedElement = head.value;
            head = head.next;
        } else {
            Node<T> nodeToRemove = getNodeByIndex(index);
            removedElement = nodeToRemove.value;
            unlink(nodeToRemove);
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, currNode.value)) {
                remove(i);
                return true;
            }
            currNode = currNode.next;
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

    private Node<T> getNodeByIndex(int index) {
        checkIfIndexInBounds(index);
        Node<T> current;
        if (index <= (size >> 1)) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIfIndexInBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + "for size: " + size);
        }
    }

    public void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

    private void addHeadToList(Node<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value) {
            this.value = value;
        }
    }

}
