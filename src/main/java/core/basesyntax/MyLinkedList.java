package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> newNode;
    private Node<T> last;
    private Node<T> first;
    private int size;

    static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T element) {
        linkLast(element);
    }

    @Override
    public void add(T element, int index) {
        isInvalidIndex(index);
        if (index == size) {
            linkLast(element);
        } else {
            linkBefore(element, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T el : list) {
            linkLast(el);
        }
    }

    @Override
    public T get(int index) {
        checkIndexPos(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T element, int index) {
        checkIndexPos(index);
        Node<T> x = findNodeByIndex(index);
        T original = x.element;
        x.element = element;
        return original;
    }

    @Override
    public T remove(int index) {
        checkIndexPos(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.element == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (object.equals(node.element)) {
                    unlink(node);
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void isInvalidIndex(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index is invalid");
        }
    }

    private void checkIndexPos(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index is invalid");
        }
    }

    private void linkBefore(T e, int index) {
        Node<T> midNode = findNodeByIndex(index);
        Node<T> someNode = midNode.prev;
        Node<T> newNode = new Node<>(someNode, e, midNode);
        midNode.prev = newNode;
        if (someNode == null) {
            first = newNode;
        } else {
            someNode.next = newNode;
        }
        size++;
    }

    void linkLast(T e) {
        Node<T> l = last;
        newNode = new Node<T>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    T unlink(Node<T> e) {
        T element = e.element;
        Node<T> next = e.next;
        Node<T> prev = e.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            e.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            e.next = null;
        }

        e.element = null;
        size--;

        return element;
    }
}
