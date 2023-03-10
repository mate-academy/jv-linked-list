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
        checkIndexInBounds(index);

        if (size == index) {
            linkLast(value);
        } else {
            Node<T> nodeIndex = getNodeIndex(index);
            Node<T> prevNode = nodeIndex.prev;
            Node<T> newNode = new Node<>(prevNode, value, nodeIndex);
            nodeIndex.prev = newNode;

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
        checkIndexInBounds(index);
        checkIndexAtSizeRange(index);
        return getNodeIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInBounds(index);
        checkIndexAtSizeRange(index);
        Node<T> nodeIndex = getNodeIndex(index);
        T changedNode = nodeIndex.value;
        nodeIndex.value = value;
        return changedNode;
    }

    @Override
    public T remove(int index) {
        checkIndexInBounds(index);
        checkIndexAtSizeRange(index);
        return unlink(getNodeIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> nodeIndex = head; nodeIndex != null; nodeIndex = nodeIndex.next) {
                if (nodeIndex.value == null) {
                    unlink(nodeIndex);
                    return true;
                }
            }
        } else {
            for (Node<T> nodeIndex = head; nodeIndex != null; nodeIndex = nodeIndex.next) {
                if (object.equals(nodeIndex.value)) {
                    unlink(nodeIndex);
                    return true;
                }
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

    private void checkIndexInBounds(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexAtSizeRange(int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
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

    private T unlink(Node<T> nodeIndex) {
        T value = nodeIndex.value;
        Node<T> next = nodeIndex.next;
        Node<T> prev = nodeIndex.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            nodeIndex.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            nodeIndex.next = null;
        }
        nodeIndex.value = null;
        size--;
        return value;
    }

    private Node<T> getNodeIndex(int index) {
        Node<T> nodeIndex;

        if (index < size) {
            nodeIndex = head;
            for (int i = 0; i < index; i++) {
                nodeIndex = nodeIndex.next;
            }
            return nodeIndex;
        } else {
            nodeIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeIndex = nodeIndex.prev;
            }
        }
        return nodeIndex;
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
