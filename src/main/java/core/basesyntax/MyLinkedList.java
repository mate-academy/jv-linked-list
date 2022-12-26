package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        final Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, false);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, true);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, true);
        Node<T> newValue = getNodeByIndex(index);
        T oldValue = newValue.value;
        newValue.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, true);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        unlink(currentNode);
        return oldValue;
    }

    @Override
    public boolean remove(T element) {
        for (Node<T> removedNode = first; removedNode
                != null; removedNode = removedNode.next) {
            if (removedNode.value == element
                    || (element != null && element.equals(removedNode.value))) {
                unlink(removedNode);
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> foundedNode;
        if (index < (size / 2)) {
            foundedNode = first;
            for (int i = 0; i < index; i++) {
                foundedNode = foundedNode.next;
            }
        } else {
            foundedNode = last;
            for (int i = size - 1; i > index; i--) {
                foundedNode = foundedNode.prev;
            }
        }
        return foundedNode;
    }

    private void unlink(Node<T> removedNode) {
        Node<T> next = removedNode.next;
        Node<T> prev = removedNode.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            removedNode.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            removedNode.next = null;
        }
        removedNode.value = null;
        size--;
    }

    private void linkBefore(T element, MyLinkedList.Node<T> afterNode) {
        Node<T> prev = afterNode.prev;
        Node<T> newNode = new MyLinkedList.Node<>(prev, element, afterNode);
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        afterNode.prev = newNode;
        size++;
    }

    private void checkIndex(int index, boolean isElement) {
        if (isElement && !(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is more then size " + size);
        } else if (!isElement && !(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is more then size " + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.value = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
