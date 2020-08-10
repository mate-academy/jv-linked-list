package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(last, value, null);
        if (size == 0) {
            first = node;
        } else if (size >= 1) {
            last.next = node;
        }
        last = node;
        size++;

        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> node = new Node<>(null, value, first);
            first.prev = node;
            first = node;
        } else {
            Node<T> node = new Node<>(findNodeWithIndex(index).prev,
                    value, findNodeWithIndex(index));
            findNodeWithIndex(index).prev.next = node;
            findNodeWithIndex(index).prev = node;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeWithIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> temp = findNodeWithIndex(index);
        T valueBeforeSetting = temp.item;
        temp.item = value;
        return valueBeforeSetting;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeWithIndex(index);
        T item = node.item;
        if (size == 1) {
            first = null;
            last = null;
        } else if (index == 0) {
            node.next.prev = null;
            first = node.next;
            node = null;
        } else if (index == size - 1) {
            node.prev.next = null;
            last = node.prev;
            node = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node = null;
        }
        size--;
        return item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == t || (node.item != null && node.item.equals(t))) {
                remove(i);
                return true;
            }
            node = node.next;
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

    public boolean checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        return true;
    }

    private Node<T> findNodeWithIndex(int index) {
        if (index <= size / 2) {
            Node<T> nodeWithIndex = first;
            for (int i = 0; i < index; i++) {
                nodeWithIndex = nodeWithIndex.next;
            }
            return nodeWithIndex;
        }

        Node<T> nodeWithIndex = last;
        for (int i = size - 1; i > index; i--) {
            nodeWithIndex = nodeWithIndex.prev;
        }
        return nodeWithIndex;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
