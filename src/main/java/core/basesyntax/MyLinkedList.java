package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (tail == null) {
            tail = node;
            head = tail;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkOutOfBounds(index);
        Node node = new Node(value);
        Node nextNode = getNode(index);
        node.prev = nextNode.prev;
        if (node.prev != null) {
            node.prev.next = node;
        } else {
            head = node;
        }
        node.next = nextNode;
        nextNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node node = getNode(index);
        T retVal = node.value;
        node.value = value;
        return retVal;
    }

    @Override
    public T remove(int index) {
        Node node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node node = head;
        while (node != null) {
            if (valueEquals(node, object)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private Node getNode(int index) {
        checkOutOfBounds(index);
        boolean reverse = index > (size / 2);
        int currIndex = reverse ? size - 1 : 0;
        Node node = reverse ? tail : head;
        if (reverse) {
            while (currIndex != index) {
                node = node.prev;
                currIndex--;
            }
        } else {
            while (currIndex != index) {
                node = node.next;
                currIndex++;
            }
        }
        return node;
    }

    private void unlink(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }

    private void checkOutOfBounds(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean valueEquals(Node node, T object) {
        return node.value == null ? object == null : node.value.equals(object);
    }
}
