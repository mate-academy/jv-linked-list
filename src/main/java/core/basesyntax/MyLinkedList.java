package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last = newNode;
        if (first == null) {
            first = last;
        } else {
            last.prev.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index < size) {
            Node<T> nodeToSetBefore = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeToSetBefore.prev, value, nodeToSetBefore);
            if (nodeToSetBefore.prev == null) {
                first = newNode;
            } else {
                nodeToSetBefore.prev.next = newNode;
            }
            nodeToSetBefore.prev = newNode;
            size++;
        } else {
            add(value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
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
        Node<T> nodeToSet = getNodeByIndex(index);
        T oldValue = nodeToSet.value;
        nodeToSet.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlinkNode(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = first;
        while (nodeToRemove != null) {
            if (object == null && nodeToRemove.value == null
                    || object != null && object.equals(nodeToRemove.value)) {
                unlinkNode(nodeToRemove);
                return true;
            }
            nodeToRemove = nodeToRemove.next;
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

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index + 1);
        Node<T> nodeToFind;
        if (index < size / 2) {
            nodeToFind = first;
            while (index > 0) {
                nodeToFind = nodeToFind.next;
                index--;
            }
        } else {
            nodeToFind = last;
            while (index < size - 1) {
                nodeToFind = nodeToFind.prev;
                index++;
            }
        }
        return nodeToFind;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds for size " + size);
        }
    }

    private void unlinkNode(Node<T> node) {
        size--;
        if (size == 0) {
            first = null;
            last = null;
        } else {
            if (node == first) {
                first = node.next;
                first.prev = null;
            } else if (node == last) {
                last = node.prev;
                last.next = null;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }
        }
    }

    class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
