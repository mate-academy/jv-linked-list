package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            linkLast(value);
        } else {
            Node<T> nodeAtIndex = getNodeIndex(index);
            Node<T> prevNode = nodeAtIndex.prev;
            Node<T> newNode = new Node<>(prevNode, value, nodeAtIndex);
            nodeAtIndex.prev = newNode;

            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNodeIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeAtIndex = getNodeIndex(index);
        T changedNode = nodeAtIndex.value;
        nodeAtIndex.value = value;
        return changedNode;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> nodeAtIndex = head; nodeAtIndex != null; nodeAtIndex = nodeAtIndex.next) {
            if (nodeAtIndex.value == object || object != null && object.equals(nodeAtIndex.value)) {
                unlink(nodeAtIndex);
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
        return size == 0;
    }

    private Node<T> getNodeIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> nodeAtIndex;

        if (index < size) {
            nodeAtIndex = head;
            for (int i = 0; i < index; i++) {
                nodeAtIndex = nodeAtIndex.next;
            }
            return nodeAtIndex;
        } else {
            nodeAtIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeAtIndex = nodeAtIndex.prev;
            }
        }
        return nodeAtIndex;
    }

    private void linkLast(T value) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<>(prevNode, value, null);
        tail = newNode;

        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> nodeAtIndex) {
        T value = nodeAtIndex.value;
        Node<T> next = nodeAtIndex.next;
        Node<T> prev = nodeAtIndex.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            nodeAtIndex.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            nodeAtIndex.next = null;
        }
        nodeAtIndex.value = null;
        size--;
        return value;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
