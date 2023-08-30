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
        if (list.isEmpty()) {
            return;
        }

        for (Object object : list) {
            T nodeElement = (T) object;
            Node<T> newNode = new Node<>(last, nodeElement, null);
            if (last == null) {
                first = newNode;
            } else {
                last.next = newNode;
            }
            last = newNode;
        }
        size += list.size();
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T item, int index) {
        checkElementIndex(index);
        Node<T> nodeValueByIndex = getNode(index);
        T oldItem = nodeValueByIndex.item;
        nodeValueByIndex.item = item;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> nodeValue = first; nodeValue != null; nodeValue = nodeValue.next) {
                if (nodeValue.item == null) {
                    unlink(nodeValue);
                    return true;
                }
            }
        } else {
            for (Node<T> nodeValue = first; nodeValue != null; nodeValue = nodeValue.next) {
                if (object.equals(nodeValue.item)) {
                    unlink(nodeValue);
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

    private void linkLast(T value) {
        final Node<T> last = this.last;
        final Node<T> newNode = new Node<>(last, value, null);
        this.last = newNode;
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    void linkBefore(T value, Node<T> succ) {
        final Node<T> pred = succ.prev;
        final Node<T> newNode = new Node<>(pred, value, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    T unlink(Node<T> value) {
        final T item = value.item;
        final Node<T> next = value.next;
        final Node<T> prev = value.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            value.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            value.next = null;
        }

        value.item = null;
        size--;
        return item;
    }

    private boolean isElementIndex(int index) {
        return (index >= 0) && (index < size);
    }

    private boolean isPositionIndex(int index) {
        return (index >= 0) && (index <= size);
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    Node<T> getNode(int index) {
        if (index < (size >> 1)) {
            Node<T> nodeElement = first;
            for (int i = 0; i < index; i++) {
                nodeElement = nodeElement.next;
            }
            return nodeElement;
        } else {
            Node<T> nodeElement = last;
            for (int i = size - 1; i > index; i--) {
                nodeElement = nodeElement.prev;
            }
            return nodeElement;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
