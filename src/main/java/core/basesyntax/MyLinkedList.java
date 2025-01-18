package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.previous = current;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> newNode = new Node<>(value);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        Node<T> lastNode = null;
        for (int i = 0; i < list.size(); i++) {
            Node<T> newNode = new Node<>(list.get(i));
            if (head == null) {
                head = newNode;
            } else {
                if (lastNode != null) {
                    lastNode.next = newNode;
                    newNode.previous = lastNode;
                }
            }
            lastNode = newNode;
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 ) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                throw new IndexOutOfBoundsException("List is in an inconsistent state: current is null at index " + i);
            }
            current = current.next;
        }

        if (current == null) {
            throw new IndexOutOfBoundsException("List is in an inconsistent state: current is null when trying to access data");
        }

        return current.data;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> removedNode;
        if (index == 0) {
            removedNode = head;
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedNode = current.next;
            current.next = current.next.next;
            if (current.next != null) {
                current.next.previous = current;
            }
        }
        size--;
        return removedNode.data;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }

        if (object == null && head.data == null) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
            size--;
            return true;
        } else if (head.data != null && head.data.equals(object)) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
            size--;
            return true;
        }

        Node<T> current = head;
        while (current != null) {
            if (object == null && current.data == null) {
                if (current.next != null) {
                    current.next.previous = current.previous;
                }
                if (current.previous != null) {
                    current.previous.next = current.next;
                }
                size--;
                return true;
            } else if (current.data != null && current.data.equals(object)) {
                if (current.next != null) {
                    current.next.previous = current.previous;
                }
                if (current.previous != null) {
                    current.previous.next = current.next;
                }
                size--;
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
        return size == 0;
    }
}
