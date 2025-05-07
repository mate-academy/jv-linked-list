package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node;
        if (size == 0) {
            node = new Node<>(null, value, null);
            head = node;
        } else {
            node = new Node<>(tail, value, null);
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexOutOfBoundsExceptionForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> indexNode;
        Node<T> newNode;
        if (index == 0) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        indexNode = findIndexNode(index);
        newNode = new Node<>(indexNode.prev, value, indexNode);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
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
        return findIndexNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue;
        oldValue = findIndexNode(index).value;
        findIndexNode(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> indexNode = findIndexNode(index);
        unlink(indexNode);
        return indexNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> objectNode = head;
        for (int i = 0; i < size; i++) {
            if ((objectNode.value == object)
                    || (objectNode.value != null && objectNode.value.equals(object))) {
                unlink(objectNode);
                return true;
            }
            objectNode = objectNode.next;
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

    private Node<T> findIndexNode(int index) {
        checkIndexOutOfBoundsException(index);
        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = head;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = tail;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode;
    }

    private void unlink(Node node) {
        if (node.prev == null) {
            //head
            if (size == 1) {
                head = null;
            } else {
                node.next.prev = null;
                head = node.next;
            }
        } else if (node.next == null) {
            //tail
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndexOutOfBoundsException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index "
                    + index + " out of bounds size " + size);
        }
    }

    private void checkIndexOutOfBoundsExceptionForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index "
                    + index + " out of bounds size " + size);
        }
    }
}
