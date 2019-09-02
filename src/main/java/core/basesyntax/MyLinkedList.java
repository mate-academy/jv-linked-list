package core.basesyntax;

import java.util.List;

import org.w3c.dom.Node;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node next;
        private Node prev;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node(value, null, null);
            last = first;
        } else {
            Node newLast = new Node(value, last, null);
            last.next = newLast;
            last = newLast;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is out of range: " + index);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> oldNode = getElement(index);
            Node<T> newNode = new Node(value, oldNode.prev, oldNode);
            if (oldNode.prev == null) {
                first = newNode;
            } else {
                oldNode.prev.next = newNode;
            }
            oldNode.prev = newNode;
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
        return getElement(index).item;
    }

    private Node<T> getElement(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> nodeIndex = first;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                nodeIndex = nodeIndex.next;
            }
        } else {
            nodeIndex = last;
            for (int i = size - 1; i > index; i--) {
                nodeIndex = nodeIndex.prev;
            }
        }
        return nodeIndex;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        getElement(index).item = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> removeElement = getElement(index);
        Node<T> next = removeElement.next;
        Node<T> previous = removeElement.prev;
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
        }
        T removedData = removeElement.item;
        removeElement = null;
        size--;
        return removedData;
    }

    @Override
    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            Node node = getElement(i);
            if (node.item.equals(t)) {
                T deletedItem = (T) node.item;
                remove(i);
                return deletedItem;
            }
        }
        return null;
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
