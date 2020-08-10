package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> node = getNodeByIndex(index);
            Node<T> prev = node.prev;
            Node<T> newNode = new Node(prev, value, node);
            node.prev = newNode;
            if (prev == null) {
                first = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        }

    }

    @Override
    public boolean addAll(List<T> list) {
        for (T itemList : list) {
            add(itemList);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldVal = node.item;
        node.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T t) {
        Node<T> x = first;
        for (int i = 0; i < size; i++) {
            if (t == x.item || t != null && t.equals((x.item))) {
                unlink(getNodeByIndex(i));
                return true;
            }
            x = x.next;
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

    private void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is wrong!");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is wrong!");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return element;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
