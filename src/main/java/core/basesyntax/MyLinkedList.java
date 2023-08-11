package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexOutOfBoundForAdd(index);
        Node<T> current = getByIndex(index);
        Node<T> newNode = new Node<T>(value, current.previous, current);
        if (current.previous == null) {
            head = newNode;
        } else {
            current.previous.next = newNode;
        }
        current.previous = newNode;
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
        return getByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getByIndex(index);
        T oldNode = node.value;
        node.value = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getByIndex(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if ((current.value != null && current.value.equals(object))
                    || (object == null && current.value == null)) {
                remove(i);
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

    private void checkIndexOutOfBoundForAdd(int index) {
        if (index < 0 || index >= size + 1) {
            throw new IndexOutOfBoundsException("Index is invalid " + index);
        }
    }

    private void checkIndexOutOfBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid " + index);
        }
    }

    private Node<T> getByIndex(int index) {
        checkIndexOutOfBound(index);
        Node<T> currentNode;
        if (index < (size / 2)) {
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

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.previous;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.previous = prevNode;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }
}
