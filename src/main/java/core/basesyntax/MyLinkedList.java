package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        } else {
            Node<T> current = getNodeByIndex(index - 1);
            newNode.next = current.next;
            newNode.prev = current.prev;
            current.next = newNode;
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
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T old = currentNode.value;
        currentNode.value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                unlink(currentNode);
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
        return first == null;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode.prev == null) {
            first = currentNode.next;
        }
        if (currentNode.next == null) {
            last = currentNode.prev;
        }
        if (currentNode.prev != null && currentNode.next != null) {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index
                    + ". Index cannot be less than 0, and equal or more than size " + size);
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index
                    + ". Index cannot be less than 0, and more than size " + size);
        }
    }
}


