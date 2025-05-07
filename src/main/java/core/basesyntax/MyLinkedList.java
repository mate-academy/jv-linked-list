package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLast(value);
        } else {
            addFirst(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addLast(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = getNodeByIndex(index);
        T oldNodeValue = oldNode.value;
        oldNode.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = first;
        while (nodeToRemove != null) {
            if (nodeToRemove.value == object
                    || (nodeToRemove.value != null && nodeToRemove.value.equals(object))) {
                unlink(nodeToRemove);
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

    private void validateIndexToGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " index is wrong for size : " + size);
        }
    }

    private void addLast(T value) {
        Node<T> tempLast = last;
        Node<T> newNode = new Node<>(tempLast, value, null);
        last = newNode;
        if (tempLast == null) {
            first = newNode;
        } else {
            tempLast.next = last;
        }
        size++;
    }

    private void addFirst(T value, Node<T> node) {
        Node<T> nodeBefore = node.prev;
        Node<T> newNode = new Node<>(nodeBefore, value, node);
        if (nodeBefore == null) {
            first = newNode;
        } else {
            nodeBefore.next = newNode;
            node.prev = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndexToGet(index);
        Node<T> temp;
        if (index < size / 2) {
            temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = last;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp;
    }

    private void unlink(Node<T> nodeToRemove) {
        if (nodeToRemove.prev == null) {
            first = nodeToRemove.next;
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
        }
        if (nodeToRemove.next == null) {
            last = nodeToRemove.prev;
        } else {
            nodeToRemove.next.prev = nodeToRemove.prev;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
