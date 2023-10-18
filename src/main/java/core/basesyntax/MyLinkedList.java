package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkLast(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    private Node<T> node(int index) {
        Node<T> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private void linkBefore(T value, Node<T> node) {
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.prev == null) {
            first = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    private void checkElementIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("There is no element with this index");
        }
    }

    private void checkPositionIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("This index is invalid");
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        Object[] arrayElements = list.toArray();
        Node<T> prev = last;
        for (Object object : arrayElements) {
            T element = (T) object;
            Node<T> newNode = new Node<>(prev, element, null);
            if (prev == null) {
                first = newNode;
            } else {
                prev.next = newNode;
            }
            prev = newNode;
            size++;
        }
        last = prev;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> temp = node(index);
        T oldItem = temp.item;
        temp.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> temp = node(index);
        Node<T> next = temp.next;
        Node<T> prev = temp.prev;
        final T element = temp.item;
        if (prev == null) {
            first = temp.next;
        } else {
            prev.next = next;
            temp.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            temp.next = null;
        }
        temp.item = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        if (object == null) {
            for (Node<T> temp = first; temp != null; temp = temp.next, i++) {
                if (temp.item == null) {
                    remove(i);
                    return true;
                }
            }
        } else {
            for (Node<T> temp = first; temp != null; temp = temp.next, i++) {
                if (object.equals(temp.item)) {
                    remove(i);
                    return true;
                }
            }
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
