package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> last = null;
    private Node<T> first;

    @Override
    public void add(T value) {
        linkedLastItem(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkedLastItem(value);
        } else {
            linkBeforeItem(value, findNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            linkedLastItem(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeByIndex = findNodeByIndex(index);
        T oldValue = nodeByIndex.item;
        nodeByIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlinkItem(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.item == null) {
                    unlinkItem(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    unlinkItem(node);
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

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private T unlinkItem(Node<T> foundNode) {
        final T removeElement = foundNode.item;
        Node<T> next = foundNode.next;
        Node<T> prev = foundNode.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            foundNode.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            foundNode.next = null;
        }

        foundNode.item = null;
        size--;
        return removeElement;
    }

    private void linkBeforeItem(T item, Node<T> foundNode) {
        Node<T> prevNode = foundNode.prev;
        Node<T> newNode = new Node<>(prevNode, item, foundNode);
        foundNode.prev = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private void linkedLastItem(T value) {
        Node<T> tempLast = last;
        Node<T> newNode = new Node<>(tempLast, value, null);
        last = newNode;
        if (tempLast == null) {
            first = newNode;
        } else {
            tempLast.next = newNode;
        }
        size++;
    }

    Node<T> findNodeByIndex(int index) {
        Node<T> fondNode;
        if (index < (size - 1)) {
            fondNode = first;
            for (int i = 0; i < index; i++) {
                fondNode = fondNode.next;
            }
            return fondNode;
        } else {
            fondNode = last;
            for (int i = size - 1; i > index; i--) {
                fondNode = fondNode.prev;
            }
            return fondNode;
        }
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range!");
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range!");
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
}
