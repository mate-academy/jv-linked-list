package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<E> implements MyLinkedListInterface<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public void add(E value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            ++size;
            return;
        }
        Node<E> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        ++size;
    }

    @Override
    public void add(E value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<E> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            ++size;
            return;
        }
        checkIndex(index);
        Node<E> newNode = findNode(index);
        Node<E> add = new Node<>(newNode.prev, value, newNode);
        newNode.prev.next = add;
        newNode.prev = add;
        size++;
    }

    @Override
    public void addAll(List<E> list) {
        for (E element : list) {
            add(element);
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        Node<E> newNode = findNode(index);
        return newNode.value;
    }

    @Override
    public E set(E value, int index) {
        Node<E> newNode = findNode(index);
        E result = newNode.value;
        newNode.value = value;
        return result;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> newNode = findNode(index);
        return unlink(newNode);
    }

    @Override
    public boolean remove(E object) {
        Node<E> newNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, newNode.value)) {
                unlink(newNode);
                return true;
            }
            newNode = newNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not correct index " + index);
        }
    }

    private Node<E> findNode(int index) {
        checkIndex(index);
        Node<E> newNode;
        if (index < size / 2) {
            newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        } else {
            newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
        }
        return newNode;
    }

    private E unlink(Node<E> node) {
        Node<E> prevNode = node.prev;
        Node<E> nextNode = node.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        E result = node.value;
        --size;
        return result;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

}
