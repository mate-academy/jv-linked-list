package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
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
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        addAll(size, list);
    }

    @SuppressWarnings("unchecked")
    public void addAll(int index, List<T> list) {
        checkPositionIndex(index);
        Object[] arr = list.toArray();
        if (arr.length == 0) {
            return;
        }
        Node<T> prev;
        Node<T> next;
        if (index == size) {
            next = null;
            prev = last;
        } else {
            next = node(index);
            prev = next.previous;
        }
        for (Object o : arr) {
            T el = (T) o;
            Node<T> newNode = new Node<>(prev, el, null);
            if (prev == null) {
                first = newNode;
            } else {
                prev.next = newNode;
            }
            prev = newNode;
        }
        if (next == null) {
            last = prev;
        } else {
            prev.next = next;
            next.previous = prev;
        }
        size += arr.length;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = node(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> n = first; n != null; n = n.next) {
                if (n.item == null) {
                    unlink(n);
                    return true;
                }
            }
        } else {
            for (Node<T> n = first; n != null; n = n.next) {
                if (object.equals(n.item)) {
                    unlink(n);
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

    private void linkBefore(T element, Node<T> s) {
        Node<T> pred = s.previous;
        Node<T> newNode = new Node<>(pred, element, s);
        s.previous = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    T unlink(Node<T> node) {
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

    Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> f = first;
            for (int i = 0; i < index; i++) {
                f = f.next;
            }
            return f;
        } else {
            Node<T> l = last;
            for (int i = size - 1; i > index; i--) {
                l = l.previous;
            }
            return l;
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
