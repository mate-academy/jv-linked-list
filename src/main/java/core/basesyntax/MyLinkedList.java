package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private int size;

    private class Node<T> {
        private T element;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node next) {
            this.element = element;
            this.next = next;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public void setFirst (Node first) {
        this.first = first;
    }
    public Node getFirst() {
        return first;
    }
    public int getSize() {
        return size; //size cannot be set, because it would not represent the actual size then.
    }

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node(value);
            size = 1;
            return;
        }
        Node current = first;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new Node(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == 0) {
            addFirst(value);
            return;
        }
        Node current = first;
        int counter = 0;
        while (counter < index - 1) {
            if (current.next == null) {
                throw new IndexOutOfBoundsException();
            }
            current = current.next;
            ++counter;
        }
        current.next = new Node(value, current.next);
        size++;
    }

    public void addFirst(T value) {
        if (first == null) {
            first = new Node(value);
            size++;
            return;
        }
        first = new Node(value, first);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Node current = first;
        while (current.next != null) {
            current = current.next;
        }
        for (int i = 0; i < list.size() - 1; ++i) {
            current.next = new Node(list.get(i));
            current = current.next;
            size++;
        }
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = first;
        int i = 0;
        do {
            if (i == index) {
                return (T) current.element;
            }
            current = current.next;
            i++;
        } while (index < size);
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node current = first;
        int counter = 0;
        while (counter < index) {
            if (current.next == null) {
                throw new IndexOutOfBoundsException();
            }
            current = current.next;
            ++counter;
        }
        T old = (T) current.element;
        current.element = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0 && first != null) {
            T value = (T) first.element;
            first = first.next;
            size--;
            return value;
        }

        Node<T> previous = first;
        int counter = 0;
        while (counter < index - 1) {
            previous = previous.next;
            if (previous == null || previous.next == null) {
                throw new IndexOutOfBoundsException();
            }
            counter++;
        }

        T value = (T) previous.next.element;
        previous.next = previous.next.next;
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        if (first == null) {
            return false;
        }

        if (areEqual(first.element, object)) {
            first = first.next;
            size--;
            return true;
        }

        Node<T> previous = first;
        while (previous.next != null) {
            if (areEqual(previous.next.element, object)) {
                previous.next = previous.next.next;
                size--;
                return true;
            }
            previous = previous.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size + 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean areEqual(Object object1, Object object2) {
        if (object1 == null || object2 == null) {
            return object1 == object2;
        }
        return object1.equals(object2);
    }
}
