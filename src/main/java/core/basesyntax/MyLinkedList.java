package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> head;
    Node<T> tail;
    private int size;
    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;
        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        final Node<T> l = tail;
        final Node<T> newNode = new Node<>(l, value, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("this index doesn't exists in linked list: " + index);
        }
        if (size == index) {
            add(value);
        } else {
            Node<T> nextNode = node(index);
            Node<T> previousNode = (index == 0) ? null : nextNode.prev;
            Node<T> newNode = new Node<>(previousNode, value, nextNode);
            if (previousNode == null) {
                head = newNode;
            } else {
                previousNode.next = newNode;
            }
            if (nextNode == null) {
                tail = newNode;
            } else {
                nextNode.prev = newNode;
            }
            size ++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeOnIndex = node(index);
        T oldvalue = nodeOnIndex.item;
        nodeOnIndex.item = value;
        return oldvalue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeOnIndex = node(index);
        T removedValue = nodeOnIndex.item;
        Node<T> prevNode = nodeOnIndex.prev;
        Node<T> nextNode = nodeOnIndex.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            nodeOnIndex.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            nodeOnIndex.next = null;
        }
        nodeOnIndex.item = null;
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        int i = 0;
        while (temp != null) {
            if (temp.item == null ? object == null : temp.item.equals(object)) {
                remove(i);
                return true;
            }
            temp = temp.next;
            i++;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("this index doesn't exists in linked list: " + index);
        }
    }

    private Node<T> node (int index) {
        Node<T> x;
        if (index < (size >> 1)) {
            x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }
}
