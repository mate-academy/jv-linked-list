package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int CAPACITY = 16;
    private int size = 0;
    private Node<T> header;
    private Node<T> currentNode;
    private Node<T> first;

    @Override
    public void add(T value) {
        if (this.isEmpty()) {
            first = new Node(value, null, header);
            header = new Node(null, first, null);
            size++;
        } else {
            currentNode = new Node((T) value, header.getPrevious(), header);
            currentNode.getPrevious().setNext(currentNode);
            currentNode.getNext().setPrevious(currentNode);
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (this.isEmpty()) {
            first = new Node(value, null, header);
            header = new Node(null, first, null);
            size++;
        } else if (index == size) {
            add(value);
        } else {
            if (getNode(index).hasPrevious() && getNode(index).hasNext()) {
                currentNode = new Node((T) value, header.getPrevious(), header);
                currentNode.getPrevious().setNext(currentNode);
                currentNode.getNext().setPrevious(currentNode);
                size++;
            }
            if (!getNode(index).hasPrevious() && getNode(index).hasNext()) {
                currentNode = new Node((T) value, header.getPrevious(), header);
                currentNode.getNext().setPrevious(currentNode);
                size++;
            }
            if (getNode(index).hasPrevious() && !getNode(index).hasNext()) {
                currentNode = new Node((T) value, header.getPrevious(), header);
                currentNode.getPrevious().setNext(currentNode);
                size++;
            }

        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T copy : list) {
            add(copy);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).getItem();
    }

    @Override
    public void set(T value, int index) {
        getNode(index).setItem(value);
    }

    @Override
    public T remove(int index) {
        Node temp = getNode(index);
        if (temp.hasPrevious()) {
            temp.getPrevious().setNext(temp.getNext());
        }
        if (temp.hasNext()) {
            temp.getNext().setPrevious(temp.getPrevious());
        }
        T toReturn = (T) temp.getItem();
        temp.setItem(null);
        size--;
        return toReturn;
    }

    @Override
    public T remove(T t) {
        Node temp = getNode(t);
        if (getNode(t) == null) {
            return null;
        }
        if (temp.hasPrevious()) {
            temp.getPrevious().setNext(temp.getNext());
        }
        if (temp.hasNext()) {
            temp.getNext().setPrevious(temp.getPrevious());
        }
        T toReturn = (T) temp.getItem();
        temp.setItem(null);
        size--;
        return toReturn;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(T value) {
        Node search = first;
        while (search.hasNext()) {
            if (search.getItem() != null && search.getItem().equals(value)) {
                return search;
            }
            search = search.next;
        }
        System.out.println("You have entered a value which doesn't exist");
        return null;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index <= size >> 1) {
            Node<T> search = first;
            for (int i = 0; i < index; i++) {
                search = search.next;
            }
            return search;
        }
        if (index > size >> 1) {
            Node<T> search = header;
            for (int i = size; i > index; i--) {
                search = search.previous;
            }
            return search;
        }
        throw new IndexOutOfBoundsException();
    }

    private static class Node<T> {
        T item;
        Node<T> previous;
        Node<T> next;

        private Node(T item, Node<T> previous, Node<T> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }

        private T getItem() {
            return this.item;
        }

        private void setItem(T item) {
            this.item = item;
        }

        private Node<T> getPrevious() {
            return this.previous;
        }

        private void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        private Node<T> getNext() {
            return this.next;
        }

        private void setNext(Node<T> next) {
            this.next = next;
        }

        private boolean hasNext() {
            return this.next != null;
        }

        private boolean hasPrevious() {
            return this.previous != null;
        }
    }
}
