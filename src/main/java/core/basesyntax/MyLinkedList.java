package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int NULL = 0;
    private static final int ONE = 1;
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexInclusive(index);

        if (index == NULL && size == NULL) {
            head = tail = new Node(tail, head, value, index);
            size++;
            return;
        }
        if (index == size) {
            tail.next = new Node(null, tail, value, index);
            tail = tail.next;
            size++;
            return;
        }

        Node current = head;

        if (index == NULL) {
            head.prev = new Node(head, null, value, index);
            head = head.prev;
            size++;
            for (int i = ONE; i < size; i++) {
                current.index = i;
                if (current.next != null) {
                    current = current.next;
                }
            }
            return;
        }
        while (index < size) {
            if (current.index < index) {
                current = current.next;
                continue;
            }
            current.prev.next = new Node(current, current.prev, value, index);
            current.prev = current.prev.next;
            size++;
            break;
        }
        updateIndex(current,index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndexExclusive(index);
        Node current = head;

        while (index < size) {
            if (current.index == index) {
                break;
            }
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndexExclusive(index);
        Node current = head;
        T oldValue = null;

        while (index < size) {
            if (current.index == index) {
                oldValue = current.value;
                current.value = value;
                break;
            }
            current = current.next;
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndexExclusive(index);
        Node current = head;
        int lastIndex = size - ONE;

        while ((current.index < index)) {
            current = current.next;
        }
        if (current.index == NULL && size == ONE) {
            size--;
            return current.value;
        }
        if (current.index == lastIndex) {
            unlink(current);
            return current.value;
        }
        if (current.index == NULL) {
            unlink(current);
        }
        if (current.index == index && current.prev != null) {
            unlink(current);
        }
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
        return size == NULL;
    }

    private class Node {
        private Node next;
        private Node prev;
        private T value;
        private int index;

        public Node(Node next, Node prev, T value, int index) {
            this.next = next;
            this.prev = prev;
            this.value = value;
            this.index = index;
        }
    }

    private void unlink(Node node) {
        int lastIndex = size - ONE;

        if (node.index == lastIndex) {
            node.prev.next = null;
            tail = node.prev;
            node.prev = null;
            size--;
        } else if (node.index == NULL) {
            node.next.prev = null;
            head = node.next;
            size--;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
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
        for (int i = index + 1; i < size; i++) {
            current.index = i;
            if (current.next != null) {
                current = current.next;
            }
        }
    }

    private void validateIndexExclusive(int index) {
        if (index < NULL || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of size:" + size);
        }
    }

    private void validateIndexInclusive(int index) {
        if (index < NULL || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of size:" + size);
        }
    }
}
