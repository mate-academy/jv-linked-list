package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            first = new Node<T>(null, value, null);
            last = first;
        } else {
            Node<T> oldLast = last;
            last = new Node<T>(oldLast, value, null);
            oldLast.setNext(last);
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index" + index);
        }
        Node<T> current = findNode(index);
        Node<T> newNode = new Node<T>(null, value, null);
        if (current.equals(first)) {
            newNode.setNext(first);
            first.setPrev(newNode);
            first = newNode;
        } else {
            current.getPrev().setNext(newNode);
            newNode.setPrev(current.getPrev());
            current.setPrev(newNode);
            newNode.setNext(current);
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
        indexCheck(index);
        Node<T> current = findNode(index);
        return current.getValue();
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> current = findNode(index);
        T returnedValue = current.value;
        Node<T> newNode = new Node<>(null, value, null);
        if (current.equals(first)) {
            newNode.setNext(first.getNext());
            first = newNode;
        } else {
            if (current.equals(last)) {
                newNode.setPrev(last.getPrev());
                last = newNode;
            } else {
                current.setValue(value);
            }
        }
        current.setValue(value);
        return returnedValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> current = findNode(index);
        letsRemoveIt(current);
        return current.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.getValue() == object || current.getValue() != null
                    && current.getValue().equals(object)) {
                letsRemoveIt(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode = first;
        while (index > 0) {
            currentNode = currentNode.getNext();
            index--;
        }
        return currentNode;
    }

    private void letsRemoveIt(Node<T> current) {
        if (current.equals(first)) {
            first = first.getNext();
        } else {
            if (current.equals(last)) {
                last = last.getPrev();
                last.setNext(null);
            } else {
                current.getPrev().setNext(current.getNext());
                current.getNext().setPrev(current.getPrev());
            }
        }
        size--;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }
}
