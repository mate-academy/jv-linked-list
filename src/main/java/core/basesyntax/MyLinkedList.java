package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.previous = previous;
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if ((index < 0 && index > size)) {
            throw new IndexOutOfBoundsException("Index beyond boundaries");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            linkLast(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (checkBoundaries(index)) {
            return getNode(index).item;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (checkBoundaries(index)) {
            T tempItem = getNode(index).item;
            getNode(index).item = value;
            return tempItem;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        if (checkBoundaries(index)) {
            return unlink(getNode(index));
        }
        return null;
    }

    @Override
    public boolean remove(T object) {

        for (Node<T> iterator = head; iterator != null; iterator = iterator.next) {
            if (iterator.item == null && object == null) {
                unlink(iterator);
                return true;
            } else if (iterator.item.equals(object)) {
                unlink(iterator);
                return true;
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

    private boolean checkBoundaries(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        throw new ArrayIndexOutOfBoundsException("Index should be within existing boundaries");
    }

    private void linkBefore(T element, Node<T> successor) {
        Node<T> predecessor = successor.previous;
        Node<T> newNode = new Node<>(predecessor, element, successor);
        successor.previous = newNode;
        if (predecessor == null) {
            head = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        if (checkBoundaries(index)) {
            Node<T> search;
            if (index < (size >> 1)) {
                search = head;
                for (int i = 0; i < index; i++) {
                    search = search.next;
                }
            } else {
                search = tail;
                for (int i = size - 1; i > index; i--) {
                    search = search.previous;
                }
            }
            return search;
        }
        return null;
    }

    private T unlink(Node<T> element) {
        final T value = element.item;
        Node<T> next = element.next;
        Node<T> previous = element.previous;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            element.previous = null;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            element.next = null;
        }

        element.item = null;
        size--;
        return value;
    }

}
