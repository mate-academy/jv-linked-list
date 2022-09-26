package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        final Node<T> link = last;
        final Node<T> node = new Node<>(link, value, null);
        last = node;
        if (link == null) {
            first = node;
        } else {
            link.next = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkFirst(value);
        } else if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            linkBefore(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            return unlinkFirst();
        } else if (index == size - 1) {
            return unlinkLast();
        } else {
            return unlink(index);
        }
    }

    @Override
    public boolean remove(T object) {
        if (getIndex(object) != -1) {
            remove(getIndex(object));
            return true;
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

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Illegal index");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private int getIndex(T object) {
        int i = 0;
        boolean isFound = false;
        Node<T> temp = first;
        for (; i < size; i++) {
            if (object == null) {
                if (temp.item == null) {
                    isFound = true;
                    break;
                }
                temp = temp.next;
            } else {
                if (object.equals(temp.item)) {
                    isFound = true;
                    break;
                }
                temp = temp.next;
            }
        }
        return isFound ? i : -1;
    }

    private void linkFirst(T value) {
        final Node<T> link = first;
        final Node<T> node = new Node<>(null, value, link);
        first = node;
        if (link == null) {
            last = node;
        } else {
            link.prev = node;
        }
        size++;
    }

    private void linkBefore(T value, int index) {
        final Node<T> link = getNode(index);
        final Node<T> node = new Node<>(link.prev, value, link);
        link.prev = node;
        node.prev.next = node;
        size++;
    }

    private T unlink(int index) {
        checkIndex(index);
        final Node<T> node = getNode(index);
        final T element = node.item;
        final Node<T> prev = node.prev;
        final Node<T> next = node.next;

        node.item = null;
        node.prev.next = next;
        node.next.prev = prev;

        size--;
        return element;
    }

    private T unlinkFirst() {
        final T value = first.item;
        final Node<T> next = first.next;
        if (next != null) {
            first.next = null;
            first = next;
            next.prev = null;
            size--;
        } else {
            first = null;
            last = null;
            size--;
        }
        return value;
    }

    private T unlinkLast() {
        final T value = last.item;
        final Node<T> prev = last.prev;
        if (prev != null) {
            last.prev = null;
            last = prev;
            prev.next = null;
            size--;
        }
        return value;
    }
}
