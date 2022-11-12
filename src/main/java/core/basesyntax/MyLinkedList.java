package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;
    private Node<T> currentNode;

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
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is not valid. Index is "
                    + index + ", but it must be in bounds from 0 to " + size);
        }
        if (index > 0 && index < size) {
            getItem(index);
            currentNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = currentNode;
            currentNode.next.prev = currentNode;
            size++;
            return;
        }
        if (index == 0 && size > 0) {
            first = new Node<>(null, value, first);
            first.next.prev = first;
            size++;
        }
        if (index == size) {
            add(value);
        }
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
        checkIndex(index);
        getItem(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        getItem(index);
        T setValue = currentNode.value;
        currentNode.value = value;
        return setValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        getItem(index);
        T removedValue = currentNode.value;
        unLink(currentNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
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

    private void getItem(int index) {
        if (index >= 0 && index < size / 2) {
            int count = 0;
            for (currentNode = first; count < size / 2; currentNode = currentNode.next) {
                if (index == count) {
                    return;
                }
                count++;
            }
        }
        if (index < size && index >= size / 2) {
            int count = size - 1;
            for (currentNode = last; count >= size / 2; currentNode = currentNode.prev) {
                if (index == count) {
                    return;
                }
                count--;
            }
        }
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
        currentNode.value = null;
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
