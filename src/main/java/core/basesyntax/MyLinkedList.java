package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int length;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<T>(null, value, null);
            tail = head;
            tail.setPrev(head);
            head.setNext(head);
            length++;
            return;
        }
        Node<T> newTail = new Node<T>(tail, value, null);
        tail.setNext(newTail);
        newTail.setPrev(tail);
        tail = newTail;
        length++;
    }

    @Override
    public void add(T value, int index) {
        if (index == length || (isEmpty() && index > -1)) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<T>(null, value, head);
            head.setPrev(newNode);
            head = newNode;
        }
        Node<T> after = getNode(index, true);
        Node<T> before = after.getPrev();
        Node<T> newNode = new Node<T>(before, value, after);
        if (after != null) {
            after.setPrev(newNode);
        }
        if (before != null) {
            before.setNext(newNode);
        }
        length++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index, false).getItem();
    }

    @Override
    public T set(T value, int index) {
        Node<T> elementToChange = getNode(index, false);
        T oldItem = elementToChange.getItem();
        elementToChange.setItem(value);
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> elementToRemove = getNode(index, false);
        if (index == length - 1) {
            elementToRemove.getPrev().setNext(null);
            tail = elementToRemove.getPrev();
        } else if (index == 0) {
            elementToRemove.getNext().setPrev(null);
            head = elementToRemove.getNext();
        } else {
            elementToRemove.getNext().setPrev(elementToRemove.getPrev());
            elementToRemove.getPrev().setNext(elementToRemove.getNext());
        }
        length--;
        return elementToRemove.getItem();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < length; i++) {
            if (current.getItem() == object
                    || current.getItem() != null
                    && current.getItem().equals(object)) {
                remove(i);
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    private Node<T> getNode(int index, boolean isItAddOperation) {
        if (isItAddOperation) {
            if (index < 0 || index > length) {
                throw new IndexOutOfBoundsException("From add Index " + index + " is out of scope");
            }
        } else {
            if (index < 0 || index >= length) {
                throw new IndexOutOfBoundsException("From add Index " + index + " is out of scope");
            }
        }
        Node<T> current;
        if (isInRightSide(index)) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = length; i > (index + 1); i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    private boolean isInRightSide(int index) {
        return (length / 2) > index;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public E getItem() {
            return item;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        private void unlink(Node<E> node) {
            prev = null;
            next = null;
        }
    }
}
