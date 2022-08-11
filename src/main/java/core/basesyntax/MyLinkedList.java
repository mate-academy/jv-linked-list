package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkElementIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else {
            Node<T> preceding = getNodeByIndex(index - 1);
            newNode.next = preceding.next;
            newNode.next.previous = newNode;
            newNode.previous = preceding;
            preceding.next = newNode;
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
        checkElementIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index, size);
        Node<T> current = getNodeByIndex(index);
        T removedValue = current.value;
        unlink(current);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = getNodeByValue(object);
        if (current != null) {
            unlink(current);
            size--;
            return true;
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

    private void checkElementIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value != null && current.value.equals(value) || current.value == value) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> preceding = node.previous;
            preceding.next = node.next;
            if (node.next == null) {
                tail = preceding;
            } else {
                node.next.previous = preceding;
            }
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.value = value;
        }
    }
}
