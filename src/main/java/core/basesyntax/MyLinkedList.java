package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        Node<T> thatNode = new Node<>(value);
        if (head == null) {
            head = tail = thatNode;
        } else if (index == 0) {
            thatNode.next = head;
            head = thatNode;
        } else if (index == size) {
            tail.next = thatNode;
            tail = thatNode;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            thatNode.next = prev.next;
            prev.next = thatNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T lis : list) {
            add(lis);
        }
    }

    @Override
    public T get(int index) {
        indexValidator(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        indexValidator(index);
        Node<T> node = getNodeByIndex(index);
        T oldNode = node.element;
        node.element = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        indexValidator(index);
        T removedElement;
        if (index == 0) {
            removedElement = head.element;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            removedElement = prev.next.element;
            prev.next = prev.next.next;
            if (index == size - 1) {
                tail = prev;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> remove = head;
        int rem = 0;
        do {
            if (object == remove.element
                    || remove.element != null && remove.element.equals(object)) {
                remove(rem);
                return true;
            }
            rem++;
            remove = remove.next;
        } while (remove.next != null);
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

    private void indexValidator(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Can't find the index" + index);
        }
    }

    private void checkIndexToAdd(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Can't added the index" + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

}

