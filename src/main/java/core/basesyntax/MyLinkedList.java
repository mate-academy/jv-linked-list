package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        linkLast(newNode);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            linkLast(newNode);
        } else if (index == 0) {
            linkFirst(newNode);
        } else {
            link(newNode, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        T oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        int removeByIndex = getIndexIfContains(object);
        if (removeByIndex == -1) {
            return false;
        }
        unlink(getNodeByIndex(removeByIndex));
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void linkLast(Node<T> newNode) {
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    private void linkFirst(Node<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    private void link(Node<T> newNode, int index) {
        Node<T> previous = getNodeByIndex(index - 1);
        newNode.next = previous.next;
        previous.next.prev = newNode;
        previous.next = newNode;
        newNode.prev = previous;
    }

    private int getIndexIfContains(T object) {
        int index = -1;
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
                return i;
            }
            current = current.next;
        }
        return index;
    }

    private T unlink(Node<T> deletedNode) {
        if (size == 1) {
            head = tail = null;
            size--;
            return deletedNode.value;
        }
        if (deletedNode.prev == null) {
            head = head.next;
            head.prev = null;
            size--;
            return deletedNode.value;
        }
        if (deletedNode.next == null) {
            tail.prev.next = null;
            tail = tail.prev;
            size--;
            return deletedNode.value;
        }
        return unlinkWithin(deletedNode);
    }

    private T unlinkWithin(Node<T> removedNode) {
        removedNode.next.prev = removedNode.prev;
        removedNode.prev.next = removedNode.next;
        size--;
        return removedNode.value;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index > size / 2) {
            return searchFromHead(index);
        } else {
            return searchFromTail(index);
        }
    }

    private Node<T> searchFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
