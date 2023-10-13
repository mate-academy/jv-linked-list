package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Not found index " + index);
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
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
        validateIndex(index);

        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> prevNode = currentNode.prev;
            newNode.next = currentNode;
            newNode.prev = prevNode;
            currentNode.prev = newNode;
            prevNode.next = newNode;
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

        if (index >= 0 && index < size) {
            return (T) getNodeByIndex(index).value;
        } else {
            throw new IndexOutOfBoundsException("Err");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public T set(T value, int index) {
        if (index >= 0 && index < size) {
            Node<T> currentNode = getNodeByIndex(index);
            T oldValue = currentNode.value;
            currentNode.value = value;
            return oldValue;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            Node<T> removedNode = getNodeByIndex(index);
            if (index == 0) {
                head = removedNode.next;
            } else if (index == size - 1) {
                tail = removedNode.prev;
            } else {
                Node<T> nextNode = getNodeByIndex(index + 1);
                Node<T> prevNode = getNodeByIndex(index - 1);
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
            }
            size--;
            return removedNode.value;
        } else {
            throw new IndexOutOfBoundsException("roror");
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object || object != null && object.equals(currentNode.value)) {
                remove(i);
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
}
