package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(value, last, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> lastNode = getNode(index);
        Node<T> firstNode = last.prev;
        Node<T> middleNode = new Node<T>(value,firstNode,lastNode);
        last.prev = middleNode;
        if (firstNode.prev == null) {
            first = middleNode;
        } else {
            firstNode.next = middleNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNode(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> deleteNode = getNode(index);
        Node<T> firstNode = deleteNode.prev;
        Node<T> lastNode = deleteNode.next;
        if (firstNode == null) {
            first = lastNode;
        } else {
            firstNode.next = lastNode;
        }
        if (lastNode == null) {
            last = firstNode;
        } else {
            lastNode.prev = firstNode;
        }
        size--;
        return deleteNode.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> newNode = first;
        for (int i = 0; i < size; i++) {
            if (t == newNode.item || t != null && t.equals(newNode.item)) {
                remove(i);
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

    private boolean checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return true;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        int initIndex;
        Node<T> node = first;
        if (index < (size >> 1)) {
            initIndex = 0;
            while (initIndex != index) {
                node = node.next;
                initIndex++;
            }
        } else {
            node = last;
            initIndex = size - 1;
            while (initIndex != index) {
                node = node.prev;
                initIndex--;
            }
        }
        return node;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
