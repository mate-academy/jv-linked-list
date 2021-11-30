package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int listSize = 0;
    private Node<T> first;
    private Node<T> last;

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

    private Node<T> nodeByIndex(int index) {
        if (index < 0 || index > listSize - 1) {
            throw new IndexOutOfBoundsException("No such index in the list");
        }

        Node<T> newNode;
        if (index < (listSize >> 1)) {
            newNode = first;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        } else {
            newNode = last;
            for (int i = listSize - 1; i > index; i--) {
                newNode = newNode.prev;
            }
        }
        return newNode;
    }

    private void addBefore(T value, Node<T> nodeAfter) {
        Node<T> nodeBefore = nodeAfter.prev;
        Node<T> addedNode = new Node<>(nodeBefore, value, nodeAfter);
        nodeAfter.prev = addedNode;
        nodeBefore.next = addedNode;
        listSize++;
    }

    private void unlink(Node<T> value) {
        Node<T> next = value.next;
        Node<T> prev = value.prev;

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
        listSize--;
    }

    @Override
    public void add(T value) {
        if (listSize == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
            first.next = last;
            last.prev = first;
        } else {
            Node<T> newLast = new Node<>(last, value, null);
            last.next = newLast;
            last = newLast;
            first.prev = null;
        }
        listSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index == listSize) {
            add(value);
            return;
        }

        Node<T> current = nodeByIndex(index);
        Node<T> prev = current.prev;
        Node<T> newNode = new Node<>(prev, value, current);

        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        current.prev = newNode;
        listSize++;
    }

    @Override
    public void addAll(List<T> list) {
        if (listSize > 0) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        return nodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = nodeByIndex(index);
        T oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        T oldValue = nodeByIndex(index).value;
        unlink(nodeByIndex(index));

        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        for (Node<T> x = first; x != null; x = x.next) {
            if (x.value == object || x.value != null && x.value.equals(object)) {
                unlink(nodeByIndex(index));
                return true;
            }
            index++;
        }
        return false;
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }
}
