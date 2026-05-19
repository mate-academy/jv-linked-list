package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node(value);

        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(value);
            return;
        }

        Node newNode = new Node(value);

        if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            Node current = getNodeByIndex(index);

            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
        }

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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node current = getNodeByIndex(index);
        T oldValue = current.value;

        current.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node current = getNodeByIndex(index);
        T oldValue = current.value;

        unlink(current);

        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node current = first;

        while (current != null) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getNodeByIndex(int index) {
        Node current;

        if (index < size / 2) {
            current = first;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;

            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    private void unlink(Node node) {
        if (node == first) {
            first = node.next;

            if (first != null) {
                first.prev = null;
            }
        } else if (node == last) {
            last = node.prev;

            if (last != null) {
                last.next = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        if (first == null) {
            last = null;
        }

        size--;
    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        Node(T value) {
            this.value = value;
        }
    }
}
