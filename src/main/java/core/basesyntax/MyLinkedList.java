package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<T>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        } else if (size == index) {
            add(value);
        } else if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
            size++;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
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
        Node<T> currentNode = getNodeByIndex(index);
        T temp = currentNode.value;
        currentNode.value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0 && size == 1) {
            T tempToReturn = head.value;
            unlinkOneNode();
            return tempToReturn;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            remove(currentNode.value);
            return currentNode.value;
        }
    }

    @Override
    public boolean remove(T value) {
        if (isEquals(head.value, value) && size == 1) {
            return unlinkOneNode();
        } else if (isEquals(head.value, value)) {
            return unlinkHeadNode();
        } else {
            return unlinkNode(value);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private boolean unlinkNode(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (isEquals(currentNode.value, value)) {
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        Node<T> currentNode = size / 2 < index ? head : tail;
        if (currentNode == head) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private boolean unlinkOneNode() {
        tail.next = null;
        tail.prev = null;
        tail.value = null;
        head = tail;
        size--;
        return true;
    }

    private boolean unlinkHeadNode() {
        head.next.prev = null;
        head = head.next;
        size--;
        return true;
    }

    private boolean isEquals(T value, T object) {
        return (value == object) || (value != null && value.equals(object));
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
