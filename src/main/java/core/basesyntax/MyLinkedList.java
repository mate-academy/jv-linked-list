package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public void setSize() {
        this.size = size;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<T>(tail, value, null);
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
        if (index == 0) {
            Node<T> newNode = new Node<T>(null, value, null);
            head.prev = newNode;
            newNode.next = head;
        } else {
            indexExist(index);
            Node<T> currentBefore = nodeGetByIndex(index - 1);
            Node<T> newNode = new Node<T>(null, value, null);
            newNode.next = currentBefore.next;
            currentBefore.next.prev = newNode;
            newNode.prev = currentBefore;
            currentBefore.next = newNode;
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
        indexExist(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        indexExist(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = nodeGetByIndex(index);
        T old = currentNode.value;
        currentNode.value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        indexExist(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = nodeGetByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object || currentNode.value != null && currentNode.value.equals(object)) {
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
        return size == 0;
    }

    private void indexExist(int index) throws IndexOutOfBoundsException {
        if (index < 0 && index > size || size == 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> nodeGetByIndex(int index) {
        indexExist(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
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
            head = currentNode.next;
        }
        if (currentNode.next == null) {
            tail = currentNode.prev;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }


    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }
    }
}
