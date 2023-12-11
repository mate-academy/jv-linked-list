package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index,false);
        newLink(value,index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index,true);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index,true);
        Node current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index,true);
        Node current = getNode(index);
        unlink(current);
        updateIndex(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;

        while ((object != null && !object.equals(current.value))
                || (current.value != null && object == null)) {
            if (current.next == null) {
                return false;
            }
            current = current.next;
        }
        return remove(current.index) != null || object == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private Node next;
        private Node previous;
        private T value;
        private int index;

        public Node(Node next, Node previous, T value, int index) {
            this.next = next;
            this.previous = previous;
            this.value = value;
            this.index = index;
        }
    }

    private Node getNode(int index) {
        Node current;
        if (size / 2 > index) {
            current = tail;
            while (current.index > index) {
                if (current.previous != null) {
                    current = current.previous;
                }
            }
        } else {
            current = head;
            while (current.index < index) {
                if (current.next != null) {
                    current = current.next;
                }
            }
        }
        return current;
    }

    private void newLink(T value, int index) {
        if (index == 0) {
            if (size == 0) {
                head = tail = new Node(tail, head, value, index);
                size++;
                return;
            }
            head.previous = new Node(head, null, value, index);
            head = head.previous;
            updateIndex(head.next,index);
            size++;
        } else if (index == size) {
            tail.next = new Node(null, tail, value, index);
            tail = tail.next;
            size++;
        } else {
            Node current = getNode(index);
            current.previous.next = new Node(current, current.previous, value, index);
            current.previous = current.previous.next;
            updateIndex(current,index);
            size++;
        }
    }

    private void unlink(Node node) {
        int lastIndex = size - 1;

        if (node.index == 0) {
            if (size == 1) {
                size--;
                return;
            }
            node.next.previous = null;
            head = node.next;
            size--;
        } else if (node.index == lastIndex) {
            node.previous.next = null;
            tail = node.previous;
            node.previous = null;
            size--;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
            node.previous = null;
            size--;
        }
    }

    private void updateIndex(Node current) {
        while (current.next != null) {
            current = current.next;
            current.index--;
        }
    }

    private void updateIndex(Node current, int index) {
        for (int i = index + 1; i <= size; i++) {
            current.index = i;
            if (current.next != null) {
                current = current.next;
            }
        }
    }

    private void validateIndex(int index, boolean exclusive) {
        if (exclusive) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + " out of size:" + size);
            }
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of size:" + size);
        }
    }
}
