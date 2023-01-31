package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
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
        Objects.checkIndex(index, size + 1);
        if (size == index) {
            add(value);
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            newNode.next.prev = newNode;
            if (index != 0) {
                newNode.prev.next = newNode;
            } else {
                head = newNode;
            }
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
        checkElementIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return removeNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == current.element || object != null && object.equals(current.element)) {
                removeNode(current);
                return true;
            }
            current = current.next;
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
        checkElementIndex(index);
        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private boolean isIndexCorrect(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isIndexCorrect(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private T removeNode(Node<T> currentNode) {
        final T element = currentNode.element;
        Node<T> next = currentNode.next;
        Node<T> prev = currentNode.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            currentNode.next = null;
        }

        currentNode.prev = null;
        size--;
        return element;
    }

    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.element = value;
            this.next = next;
        }
    }
}

