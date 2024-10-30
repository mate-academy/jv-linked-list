package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        linkLastElement(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        if (index == size) {
            linkLastElement(value);
        } else {
            linkBeforeElement(value, searchForNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list.isEmpty()) {
            return;
        }
        Node<T> previous = last;
        T[] elements = (T[]) list.toArray();
        for (T element : elements) {
            Node<T> newNode = new Node<>(previous, element, null);
            if (previous == null) {
                first = newNode;
            } else {
                previous.next = newNode;
                previous = newNode;
            }
            last = previous;
        }
        size += elements.length;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchForNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> temp = searchForNode(index);
        T oldValue = temp.item;
        temp.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(searchForNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> temp = first; temp != null; temp = temp.next) {
            if (object != null && object.equals(temp.item) || temp.item == object) {
                unlink(temp);
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

    private void linkLastElement(T element) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(last, element, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    private void linkBeforeElement(T element, Node<T> next) {
        Node<T> oldPrev = next.prev;
        Node<T> newNode = new Node<>(next.prev, element, next);
        next.prev = newNode;
        if (oldPrev == null) {
            first = newNode;
        } else {
            oldPrev.next = newNode;
        }
        size++;
    }

    private void checkIndexToAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
    }

    private Node<T> searchForNode(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> nodeToUnlink) {
        final T elementToRemove = nodeToUnlink.item;
        Node<T> next = nodeToUnlink.next;
        Node<T> previous = nodeToUnlink.prev;
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            nodeToUnlink.prev = null;
        }
        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
            nodeToUnlink.next = null;
        }
        nodeToUnlink.item = null;
        size--;
        return elementToRemove;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
