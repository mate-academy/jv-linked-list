package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int elementsData;

    public MyLinkedList() {
        first = new Node<T>(null, null, null);;
        last = new Node<T>(null, null, null);;
        elementsData = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<T>(null, value, null);
        if (elementsData == 0) {
            first = node;
            last = node;
            elementsData += 1;
            return true;
        }
        last.next = node;
        node.prev = last;
        last = node;
        elementsData += 1;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > elementsData || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " Size: " + elementsData);
        }
        Node<T> node = new Node<T>(null, value, null);
        if (index == 0) {
            first.prev = node;
            node.next = first;
            first = node;
        }
        if (index == elementsData) {
            add(value);
            return;
        }
        if (index > 0 && index < elementsData) {
            Node<T> buffer = searching(index);
            buffer.prev.next = node;
            node.next = buffer;
            node.prev = buffer.prev;
            buffer.prev = node;
        }
        this.elementsData += 1;
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
        if (index < elementsData && index >= 0) {
            return searching(index).item;
        }
        throw new IndexOutOfBoundsException("Index: " + index + " Size: " + elementsData);
    }

    @Override
    public T set(T value, int index) {
        if (index > elementsData - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " Size: " + elementsData);
        }
        Node<T> buffer = searching(index);
        T item = buffer.item;
        buffer.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        if (index > elementsData - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " Size: " + elementsData);
        }
        Node<T> buffer = searching(index);
        if (elementsData == 1) {
            first = null;
            last = null;
        }
        if (index == 0 && elementsData > 1) {
            first = first.next;
            first.prev = null;
        }
        if (index == elementsData - 1 && elementsData > 1) {
            last = last.prev;
            last.next = null;
        }
        if (index > 0 && index < elementsData - 1) {
            buffer.prev.next = buffer.next;
            buffer.next.prev = buffer.prev;
        }
        elementsData -= 1;
        return buffer.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> buffer = first;
        for (int i = 0; i < elementsData; i++) {
            if (t == null ? buffer.item == null : buffer.item.equals(t)) {
                remove(i);
                return true;
            }
            buffer = buffer.next;
        }
        return false;
    }

    private Node<T> searching(int index) {
        Node<T> buffer = first;
        for (int i = 0; i < index; i++) {
            buffer = buffer.next;
        }
        return buffer;
    }

    @Override
    public int size() {
        return this.elementsData;
    }

    @Override
    public boolean isEmpty() {
        return this.elementsData == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> buffer = first;
        while (buffer != last) {
            result.append(buffer.item + ", ");
            buffer = buffer.next;
        }
        result.append(last != null ? last.item : "" + "]");
        return result.toString();
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return this.item + "";
        }
    }
}
