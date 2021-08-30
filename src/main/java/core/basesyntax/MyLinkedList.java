package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            createFirstNode(value);
        } else {
            Node<T> nextNode = new Node<>(tail, value, null);
            tail.next = nextNode;
            tail = nextNode;
        }
    }

    @Override
    public void add(T value, int index) {
        indexIsValidAdd(index);
        if (index == size()) {
            add(value);
        } else if (index == 0) {
            addToTheBegining(value);
        } else {
            addToTheMiddle(value, index);
        }
    }

    private void addToTheBegining(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
    }

    private void addToTheMiddle(T value, int index) {
        Node<T> current = head;
        int count = 0;
        while (count++ != index) {
            current = current.next;
        }
        Node<T> newNode = new Node<>(current.prev, value, current);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        T oldValue = current.element;
        if (current.prev != null && current.next != null) {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        } else if (current == tail && current.prev != null) {
            current.prev.next = null;
            tail = current.prev;
        } else if (current == head && current.next != null) {
            current.next.prev = null;
            head = current.next;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size(); i++) {
            T currentElement = getNode(i).element;
            if ((currentElement == object)
                    || (currentElement != null
                    && currentElement.equals(object))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private boolean indexIsValidAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index are not exist");
        }
        return true;
    }

    private boolean indexIsValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is not exist");
        }
        return true;
    }

    private Node<T> getNode(int index) {
        indexIsValid(index);
        int count = 0;
        Node<T> current = head;
        while (count++ != index) {
            current = current.next;
        }
        return current;
    }

    private void createFirstNode(T value) {
        Node<T> firstNode = new Node<>(null, value, null);
        head = firstNode;
        tail = firstNode;
    }

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
            size++;
        }
    }
}
