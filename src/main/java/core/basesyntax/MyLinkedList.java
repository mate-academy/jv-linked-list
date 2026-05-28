package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node {
        private T value;
        private Node next;
        private Node prev;

        public Node(T value, Node prev) {
            this.value = value;
            this.prev = prev;
            this.next = null;
        }
    }

    private int size = 0;
    private Node first;
    private Node last;

    public MyLinkedList() {
    }

    private Node getNode(int index) {
        if (index < size / 2) {
            Node current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node(value, null);
            last = first;
        } else {
            Node newNode = new Node(value, last);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node newNode = new Node(value, null);
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            size++;
            return;
        }
        Node current = getNode(index);
        Node newNode = new Node(value, current.prev);
        newNode.next = current;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node targetNode = getNode(index);
        T oldValue = (T) targetNode.value;
        targetNode.value = value;
        return oldValue;
    }

    private void unlink(Node current) {
        if (size == 1) {
            first = null;
            last = null;
        } else if (current == first) {
            first = first.next;
            first.prev = null;
        } else if (current == last) {
            last = last.prev;
            last.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node target = getNode(index);
        T removedValue = target.value;
        unlink(target);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node current = first;
        while (current != null) {
            if ((object == null && current.value == null) || (object != null
                    && object.equals(current.value))) {
                unlink(current);
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
