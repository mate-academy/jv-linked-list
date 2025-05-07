package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
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
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> n = first; n != null; n = n.next) {
            if (object == null && n.item == null
                    || object != null && object.equals(n.item)) {
                unlink(n);
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

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of size.");
        }
    }

    private void checkElementIndex(int index) {
        if (!isElementPosition(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of size.");
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private boolean isElementPosition(int index) {
        return index >= 0 && index < size;
    }

    private void linkLast(T element) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, element, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> node) {
        Node<T> pred = node.previous;
        Node<T> newNode = new Node<>(pred, element, node);
        node.previous = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.previous;
        Node<T> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.previous = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.previous = prev;
            node.next = null;
        }
        T element = node.item;
        size--;
        return element;
    }

    private Node<T> getNode(int index) {
        if (index < (size >> 1)) {
            Node<T> firstPointer = first;
            for (int i = 0; i < index; i++) {
                firstPointer = firstPointer.next;
            }
            return firstPointer;
        } else {
            Node<T> lastPointer = last;
            for (int i = size - 1; i > index; i--) {
                lastPointer = lastPointer.previous;
            }
            return lastPointer;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }
}
