package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail,value,null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkException(index);
        Node<T> currentNode = searchNode(index);
        if (index == 0) {
            head = insertFront(null, value, currentNode);
        } else {
            Node<T> prevNode = currentNode.prev;
            prevNode.next = insertFront(prevNode, value, currentNode);
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
        checkException(index);
        return searchNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkException(index);
        Node<T> changedNode = searchNode(index);
        T pastValue = changedNode.value;
        changedNode.value = value;
        return pastValue;
    }

    @Override
    public T remove(int index) {
        checkException(index);
        Node<T> deletedNode = searchNode(index);
        unlink(index, deletedNode);
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                unlink(i, currentNode);
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

    private Node<T> searchNode(int index) {
        Node<T> currentNode;
        if (index < size * 0.5) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds exception");
        }
    }

    private void unlink(int index, Node<T> deletedNode) {
        if (size == 1) {
            size = 0;
            head = null;
            tail = null;
            return;
        }
        if (index == 0) {
            deletedNode.next.prev = null;
            head = deletedNode.next;
        } else if (index == size - 1) {
            deletedNode.prev.next = null;
            tail = deletedNode.prev;
        } else {
            deletedNode.prev.next = deletedNode.next;
            deletedNode.next.prev = deletedNode.prev;
        }
        size--;
    }

    private Node<T> insertFront(Node<T> prevNode, T value, Node<T> currentNode) {
        Node<T> insertedNode = new Node<>(prevNode, value, currentNode);
        currentNode.prev = insertedNode;
        return insertedNode;
    }
}
