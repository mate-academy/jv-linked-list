package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        // adding the node at the end of the LinkedList
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        // adding the node into certain place of the list
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head = newNode;
                newNode.next.prev = newNode;
            }
        } else if (index == size) {
            newNode.next = null;
            newNode.prev = tail;
            tail = newNode;
            newNode.prev.next = newNode;
        } else {
            // adding the node in the middle
            try {
                Node finderNode = head;
                for (int i = 0; i < index; i++) {
                    finderNode = finderNode.next;
                }
                newNode.next = finderNode;
                newNode.prev = finderNode.prev;
                finderNode.prev = newNode;
                newNode.next.prev = newNode;
                newNode.prev.next = newNode;
            } catch (NullPointerException e) {
                throw new IndexOutOfBoundsException("Exception in thread \"main\" "
                        + "java.lang.IndexOutOfBoundsException");
            }

        }
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        // inserting another LinkedList into the current LinkedList
        for (T value : list) {
            Node<T> newNode = new Node<>(value);
            if (isEmpty()) {
                head = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
            }
            tail = newNode;
            size++;
        }
    }

    @Override
    public T get(int index) {
        // getting the node by index
        try {
            Node value = head;
            for (int i = 0; i < index; i++) {
                value = value.next;
            }
            return (T) value.value;
        } catch (NullPointerException e) {
            throw new IndexOutOfBoundsException("Exception in thread \"main\" "
                    + "java.lang.IndexOutOfBoundsException");
        }

    }

    @Override
    public T set(T value, int index) {
        // setting the node by index
        Node newNode = new Node<>(value);
        T returnValue = get(index);

        if (index == 0) {
            head.next.prev = newNode;
            newNode.next = head.next;
            head = newNode;
        } else {
            Node finderNode = head;
            for (int i = 0; i < index; i++) {
                finderNode = finderNode.next;
            }
            newNode.next = finderNode.next;
            newNode.prev = finderNode.prev;
            finderNode.next.prev = newNode;
            finderNode.prev.next = newNode;
        }

        return returnValue;
    }

    @Override
    public T remove(int index) {
        // removing the node by index
        T returnValue = get(index);
        try {
            Node deleteNode = head;
            for (int i = 0; i < index; i++) {
                deleteNode = deleteNode.next;
            }
            if (index == 0) {
                if (size != 1) {
                    head = deleteNode.next;
                    deleteNode.next.prev = deleteNode.prev;
                }
            } else if (index == size - 1) {
                tail = deleteNode.prev;
                deleteNode.prev.next = deleteNode.next;
            } else {
                deleteNode.prev.next = deleteNode.next;
                deleteNode.next.prev = deleteNode.prev;
            }
        } catch (NullPointerException e) {
            throw new IndexOutOfBoundsException("Exception in thread \"main\" "
                    + "java.lang.IndexOutOfBoundsException");
        }
        size--;
        return returnValue;
    }

    @Override
    public boolean remove(T object) {
        // remove the node and return result(true/false)
        Boolean elementFound = false;
        Node<T> finderNode = head;
        Node<T> prevNode = new Node<>(object);
        for (int i = 0; i < size; i++) {
            if (finderNode.value == null && object != null) {
                prevNode = finderNode;
                finderNode = finderNode.next;
            } else if ((finderNode.value == null && object == null)
                    || (finderNode.value.equals(object))) {
                prevNode.next = finderNode.next;
                finderNode = finderNode.next;
                if (head.value == object) {
                    head = finderNode;
                }
                elementFound = true;
                size--;
                break;
            } else {
                prevNode = finderNode;
                finderNode = finderNode.next;
            }
        }
        return elementFound;
    }

    @Override
    public int size() {
        // LinkedList size
        return size;
    }

    @Override
    public boolean isEmpty() {
        // is empty LinkedList?
        return size == 0;
    }

    private class Node<T> {
        private T value;
        private Node next;
        private Node prev;

        Node(T value) {
            this.value = value;
        }
    }
}
