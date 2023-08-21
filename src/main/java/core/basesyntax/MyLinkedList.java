package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int MAX_ARRAY_SIZE = 10;
    private Node[] nodes = new Node[MAX_ARRAY_SIZE];
    private int size;
    private Node head;
    private Node tail;

    private class Node<T> {
        private Node prev;
        private Node next;
        private T value;

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        grow();
        nodes[size] = new Node(value);
        tail = nodes[size];
        if (size == 0) {
            head = nodes[0];
            size++;
            return;
        }

        nodes[size].prev = nodes[size - 1];
        nodes[size - 1].next = nodes[size];
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index " + index + " is out of size " + size);
        }

        if (size == 0 || index == size) {
            add(value);
            return;
        }

        grow();
        checkIndex(index);
        Node nodeIndex = new Node(value);
        if (index == 0) {
            System.arraycopy(nodes, index, nodes, index + 1, size - index);
            nodes[index].prev = nodeIndex;
            nodeIndex.next = nodes[index];
            nodes[index] = nodeIndex;
            head = nodes[0];
            tail = nodes[size];
            size++;
            return;
        }

        System.arraycopy(nodes, index, nodes, index + 1, size - index);
        nodes[index - 1].next = nodeIndex;
        nodes[index].prev = nodeIndex;
        nodeIndex.prev = nodes[index - 1];
        nodeIndex.next = nodes[index];
        nodes[index] = nodeIndex;
        tail = nodes[size];
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
        return (T) nodes[index].value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T changed = (T)nodes[index].value;
        nodes[index] = new Node<>(value);

        if (index == 0) {
            nodes[index].next = nodes[index + 1];
            nodes[index + 1].prev = nodes[index];
            head = nodes[0];
            return changed;
        }

        if (index == size - 1) {
            nodes[index].prev = nodes[index - 1];
            nodes[index - 1].next = nodes[index].prev;
            tail = nodes[size - 1];
            return changed;
        }

        nodes[index].next = nodes[index + 1];
        nodes[index].prev = nodes[index - 1];
        nodes[index + 1].prev = nodes[index];
        nodes[index - 1].next = nodes[index];

        return changed;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node removed = nodes[index];
        unlink(removed);
        size--;
        System.arraycopy(nodes, index + 1, nodes, index, size - index);
        head = nodes[0];
        tail = nodes[size];
        return (T)removed.value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (nodes[i].value != null && nodes[i].value.equals(object)
                    || nodes[i].value == null && object == null) {
                T removed = remove(i);
                return removed != null && removed.equals(object)
                        || removed == null && object == null;
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
        return size == 0;
    }

    private void grow() {
        if (size >= nodes.length) {
            System.arraycopy(nodes, 0, nodes =
                    new Node[size + MAX_ARRAY_SIZE / 2], 0, size);
        }
    }

    private void unlink(Node node) {
        if (node.next == null && node.prev == null) {
            return;
        }

        if (node.next == null) {
            node.prev.next = null;
            return;
        }

        if (node.prev == null) {
            node.next.prev = null;
            return;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index "
                    + index + " is equals or out of size " + size);
        }
    }
}
