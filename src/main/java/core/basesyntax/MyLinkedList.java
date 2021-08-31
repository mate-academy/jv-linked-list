package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(tail, value, null);
        if (size > 0) {
            tail.next = addedNode;
        } else {
            head = addedNode;
        }
        tail = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkPositionIndex(index);
        if (index == 0) {
            Node<T> addedNode = new Node<>(null, value, head);
            head.previous = addedNode;
            head = addedNode;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> prevNode = currentNode.previous;
            Node<T> addedNode = new Node<>(prevNode, value, currentNode);
            currentNode.previous = addedNode;
            prevNode.next = addedNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> currentNode = node(index);
        T oldVal = currentNode.value;
        currentNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.value)) {
                    unlink(x);
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

    private boolean isPositionIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.previous;
            }
            return x;
        }
    }

    T unlink(Node<T> x) {
        final T element = x.value;
        final Node<T> next = x.next;
        final Node<T> prev = x.previous;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.previous = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
            x.next = null;
        }
        x.value = null;
        size--;
        return element;
    }

    private boolean indexInSecondHalf(int index) {
        return index >= size / 2;
    }

    private Node<T> getNode(int index) {
        Node<T> neededNode = head;
        if (indexInSecondHalf(index)) {
            neededNode = tail;
            while (index < size - 1) {
                neededNode = neededNode.previous;
                index++;
            }
        } else {
            while (index > 0) {
                neededNode = neededNode.next;
                index--;
            }
        }
        return neededNode;
    }
}
