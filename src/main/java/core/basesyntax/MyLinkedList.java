package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int length;


    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = tail = new Node<T>(null, value, null);
            //tail = head;
        } else {
            tail.next = new Node<T>(tail, value, null);
            tail = tail.next;
        }
        length++;
    }

    @Override
    public void add(T value, int index) {
        isIndexValidForAdd(index);
        if (index == length) {
            add(value);
            return;
        }
        Node<T> nextNode = getNode(index);
        Node<T> prevNextNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNextNode, value, nextNode);
        nextNode.prev = newNode;
        if (prevNextNode != null) {
            nextNode.next = newNode;
        } else {
            head = newNode;
        }
        length++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        isIndexValidForGet(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    private void isIndexValidForGet(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + " is out of scope");
        }
    }

    private void isIndexValidForAdd(int index) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Index: " + index + " is out of scope");
        }
    }

    private Node<T> getNode(int index) {
        isIndexValidForGet(index);
        Node<T> current;
        if ((length / 2) > index) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = length; i > (index + 1); i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        length--;
        if (node.next == null) {
            if (node.prev != null) {
                node.prev.next = null;
            }
            tail = node.prev;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        private Node(Node<E> prev, E item,  Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
