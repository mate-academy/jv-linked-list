package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(value, lastNode, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(value);
        } else {
            addBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNodeByIndex(index).element;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNodeByIndex(index);
        node.element = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> removedNode = getNodeByIndex(index);
        T removedElement = removedNode.element;
        unlink(removedNode);
        return removedElement;
    }

    @Override
    public T remove(T t) {
        T removedElement = null;
        for (Node<T> node = first; node != null; node = node.next) {
            if (t == null || t.equals(node.element)) {
                removedElement = node.element;
                unlink(node);
                ;
            }
        }
        return removedElement;
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
        if (index < (size >> 1)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
            return node;
        }
    }

    private void addBefore(T value, Node<T> node) {
        Node<T> previousNode = node.previous;
        Node<T> newNode = new Node<>(value, previousNode, node);
        node.previous = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> previous = node.previous;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            node.previous = null;
        }

        if (next == null) {
            last = previous;
        } else {
            next.previous = previous;
            node.next = null;
        }

        node.element = null;
        size--;
    }

    private static class Node<T> {
        private T element;
        Node<T> next;
        Node<T> previous;

        public Node(T element, Node<T> previous, Node<T> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
