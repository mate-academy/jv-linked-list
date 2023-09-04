package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;

    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(T element) {
            this.element = element;
        }
    }

    void isIndexOk(int index, int size) {
        if (index < 0 || index + 1 > size) {
            throw new IndexOutOfBoundsException("Index can`t be < 0 and > size, index = " + index
                    + " size = " + size);
        }
    }

    public Node<T> getNode(int index) {
        Node<T> oldNode = first;
        for (int i = 0; i < index; i++) {
            oldNode = oldNode.next;
        }
        return oldNode;
    }

    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if (first == null) {
            newNode.next = null;
            newNode.previous = null;
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T element, int index) {
        isIndexOk(index, size + 1);
        Node<T> newNode = new Node(element);
        if (index == size) {
            add(element);
        } else if (index == 0) {
            Node<T> oldNode = first;
            oldNode.previous = newNode;
            newNode.next = oldNode;
            first = newNode;
            size++;
        } else {
            Node<T> oldNode = getNode(index);
            Node<T> oldPrevious = oldNode.previous;
            oldPrevious.next = newNode;
            oldNode.previous = newNode;
            newNode.previous = oldPrevious;
            newNode.next = oldNode;
            size++;
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
        isIndexOk(index, size);
        Node<T> result = getNode(index);
        return result.element;
    }

    @Override
    public T set(T value, int index) {
        isIndexOk(index,size);
        Node<T> node = first;
        int count = 0;
        while (node.next != null) {
            if (count == index) {
                T oldValue = node.element;
                node.element = value;
                return oldValue;
            }
            count++;
            node = node.next;
        }
        return node.element;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T remove(int index) {
        isIndexOk(index, size);
        Node<T> result = getNode(index);
        T element = result.element;
        Node<T> prev = result.previous;
        Node<T> next = result.next;
        if (size == 1) {
            first = null;
            last = null;

            size--;
            return element;
        }

        if (next == null) {
            prev.next = null;
            last = prev;
            size--;
        } else {

            if (prev == null) {
                next.previous = null;
                first = next;
                size--;
            } else {
                prev.next = next;
                next.previous = prev;
                size--;
            }
        }
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object == null && node.element == null) {
                remove(i);
                return true;
            }
            if (object != null && node.element != null && node.element.equals(object)) {
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
}
