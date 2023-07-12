package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
        if (index == size) {
            addLast(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            Node<T> nodeByRightHand = findNodeByIndex(index);
            Node<T> nodeByLeftHand = new Node<>(nodeByRightHand.prev, value, nodeByRightHand);
            nodeByRightHand.prev.next = nodeByLeftHand;
            nodeByRightHand.prev = nodeByLeftHand;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            addLast(t);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> nodeForReplace = findNodeByIndex(index);
        T previous = nodeForReplace.item;
        nodeForReplace.item = value;
        return previous;

    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> nodeForRemove = findNodeByIndex(index);
        unlink(nodeForRemove);
        return nodeForRemove.item;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        Node<T> nodeForRemove = first;
        while (i < size) {
            i++;
            if (nodeForRemove.item == object
                    || nodeForRemove.item != null && nodeForRemove.item.equals(object)) {
                unlink(nodeForRemove);
                return true;
            }
            nodeForRemove = nodeForRemove.next;
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

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
    }

    private void addFirst(T object) {
        Node<T> newFirst = new Node<>(object, first);
        if (first != null) {
            first.prev = newFirst;
        }
        if (last == null) {
            last = newFirst;
        }
        first = newFirst;
        size++;
    }

    private void addLast(T object) {
        Node<T> newLast = new Node<>(last, object);
        if (last != null) {
            last.next = newLast;
        }
        if (first == null) {
            first = newLast;
        }
        last = newLast;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index > size / 2) {
            return scrollFromLast(last, size - 1 - index);
        }
        return scrollFromFirst(first, index);
    }

    private Node<T> scrollFromLast(Node<T> last, int steps) {
        if (steps == 0) {
            return last;
        }
        steps--;
        return scrollFromLast(last.prev, steps);
    }

    private Node<T> scrollFromFirst(Node<T> first, int steps) {
        if (steps == 0) {
            return first;
        }
        steps--;
        return scrollFromFirst(first.next, steps);
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }

        Node(Node<T> prev, T element) {
            this.prev = prev;
            this.item = element;
        }

        Node(T element, Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}
