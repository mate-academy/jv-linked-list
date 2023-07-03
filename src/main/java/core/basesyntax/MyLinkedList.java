package core.basesyntax;

import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public MyLinkedList() {
    }

    public MyLinkedList(Collection<? extends T> list) {
        this();
        addAll((List<T>) list);
    }

    @Override
    public void add(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    void linkLast(T value) {
        final Node<T> oldLast = last;
        final Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    void linkBefore(T value, Node<T> successor) {
        final Node<T> predecessor = successor.prev;
        final Node<T> newNode = new Node<>(predecessor, value, successor);
        successor.prev = newNode;
        if (predecessor == null) {
            first = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> nodeToFind = first;
            for (int i = 0; i < index; i++) {
                nodeToFind = nodeToFind.next;
            }
            return nodeToFind;
        } else {
            Node<T> nodeToFind = last;
            for (int i = size - 1; i > index; i--) {
                nodeToFind = nodeToFind.prev;
            }
            return nodeToFind;
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds");
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void addAll(List<T> list) {
        checkPositionIndex(size);
        Object[] arrayOfList = list.toArray();
        int numNew = arrayOfList.length;
        if (numNew != 0) {
            Node<T> predecessor = last;
            for (Object object : arrayOfList) {
                Node<T> newNode = new Node<>(predecessor, (T) object, null);
                predecessor.next = newNode;
                predecessor = newNode;
            }
            last = predecessor;
            size += numNew;
        }
    }
    /////////////////////////////////////////////////////////////////////////////

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + "is out of bounds");
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    //////////////////////////////////////////////////////////////////////////////
    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if ((object == null && x.item == null) || (x.item != null && x.item.equals(object))) {
                unlink(x);
                return true;
            }
        }
        return false;
    }

    T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
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
        node.item = null;
        size--;
        return element;
    }

    /////////////////////////////////////////////////////////////////////////////////
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
}
