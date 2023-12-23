package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> currentNode;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (currentNode == null) {
            currentNode = new Node<>(null, value, null);
            head = currentNode;
            tail = currentNode;
        } else {
            currentNode = tail;
            Node<T> nodeToAdd = new Node<>(currentNode, value, null);
            currentNode.next = nodeToAdd;
            currentNode = nodeToAdd;
            tail = currentNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Can't find the element by index " + index);
        }
        Node<T> nodeToAdd;
        if (index == 0 && size == 0) {
            add(value);
        } else if (index == 0) {
            currentNode = head;
            nodeToAdd = new Node<>(null, value, currentNode);
            currentNode.prev = nodeToAdd;
            head = nodeToAdd;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            getIndexedNode(index);
            nodeToAdd = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = nodeToAdd;
            currentNode.prev = nodeToAdd;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        getIndexedNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        getIndexedNode(index);
        T result = currentNode.value;
        currentNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T result = unlink(index);
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) {
        currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                T element = unlink(i);
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
        return size == 0;
    }

    private void getIndexedNode(int index) {
        if (index >= size / 2) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
    }

    private T unlink(int index) {
        T result = null;
        if (index == 0 && size == 1) {
            currentNode = null;
            head = null;
            tail = null;
        } else if (index == 0) {
            currentNode = head;
            result = currentNode.value;
            currentNode.next.prev = null;
            currentNode = currentNode.next;
            head = currentNode;
        } else if (index == (size - 1)) {
            currentNode = tail;
            result = currentNode.value;
            currentNode.prev.next = null;
            currentNode = currentNode.prev;
            tail = currentNode;
        } else {
            getIndexedNode(index);
            result = currentNode.value;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        return result;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Can't find the element by index " + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
