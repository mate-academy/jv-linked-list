package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
            return;
        }
        last = new Node<>(last, value, null);
        last.prev.next = last;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0 && size > 0) {
            first = new Node<>(null, value, first);
            first.next.prev = first;
            size++;
            return;
        }
        Node<T> nodeByIndex = getNodeByIndex(index);
        Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
        newNode.prev.next = newNode;
        nodeByIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            last = new Node<>(last, list.get(i), null);
            last.prev.next = last;
            size++;
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T setValue = currentNode.value;
        currentNode.value = value;
        return setValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T removedValue = currentNode.value;
        unLink(currentNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode;
        for (currentNode = first; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                unLink(currentNode);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is not valid. Index is "
                    + index + ", but it must be in bounds from 0 to " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = first;
        if (index >= 0 && index < size / 2) {
            int count = 0;
            for (currentNode = first; count < size / 2; currentNode = currentNode.next) {
                if (index == count) {
                    return currentNode;
                }
                count++;
            }
        }
        if (index < size && index >= size / 2) {
            int count = size - 1;
            for (currentNode = last; count >= size / 2; currentNode = currentNode.prev) {
                if (index == count) {
                    return currentNode;
                }
                count--;
            }
        }
        return currentNode;
    }

    private void unLink(Node<T> currentNode) {
        Node<T> prev = currentNode.prev;
        Node<T> next = currentNode.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            currentNode.prev = prev;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            currentNode.next = next;
        }
        size--;
    }

    private static class Node<T> {
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
