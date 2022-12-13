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
        } else {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            checkIndex(index);
            first.prev = new Node<>(null, value, first);
            first = first.prev;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = newNode;
            nodeByIndex.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = getNodeByIndex(index);
        T oldValue = nodeByIndex.item;
        nodeByIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object == null && node.item == null
                    || node.item != null && node.item.equals(object)) {
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

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private T unlink(Node<T> node) {
        final Node<T> oldNode;
        if (size == 1) {
            oldNode = first;
            first = null;
            last = null;
            size--;
        } else if (node.equals(first)) {
            oldNode = first;
            first.next.prev = null;
            first = first.next;
            size--;
        } else if (node.equals(last)) {
            oldNode = last;
            last.prev.next = null;
            last = last.prev;
            size--;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
            return node.item;
        }
        return oldNode.item;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Passed index is invalid");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> nodeByIndex;
        if (index > (size / 2)) {
            nodeByIndex = last;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
        } else {
            nodeByIndex = first;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
        }
        return nodeByIndex;
    }
}
