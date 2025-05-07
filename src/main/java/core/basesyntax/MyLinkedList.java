package core.basesyntax;

import java.util.List;

public class MyLinkedList<E> implements MyLinkedListInterface<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

    @Override
    public void add(E value) {
        if (size == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(E value, int index) {
        checkPositionIndex(index);
        if (size == 0 || index == 0) {
            linkFirst(value);
        } else {
            if (index == size) {
                linkLast(value);
            } else {
                Node<E> x = node(index);
                Node<E> newNode = new Node<>(x.prev, value, x);
                x.prev.next = newNode;
                x.prev = newNode;
                size++;
            }
        }
    }

    @Override
    public void addAll(List<E> list) {
        for (E item : list) {
            add(item);
        }
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public E set(E value, int index) {
        checkElementIndex(index);
        if (index == 0) {
            Node<E> f = first;
            unLinkFirst(f);
            linkFirst(value);
            return f.item;
        } else {
            Node<E> x = node(index);
            unLink(x);
            add(value, index);
            return x.item;
        }
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        if (index == 0) {
            Node<E> f = first;
            unLinkFirst(f);
            return f.item;
        } else if (index == size - 1) {
            Node<E> l = last;
            unLinkLast(l);
            return l.item;
        } else {
            Node<E> x = node(index);
            unLink(x);
            return x.item;
        }
    }

    @Override
    public boolean remove(E object) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (object == null && x.item == null) {
                unLink(x);
                return true;
            }
            if (object != null && object.equals(x.item)) {
                if (size == 1 || object.equals(first.item)) {
                    unLinkFirst(x);
                } else {
                    unLink(x);
                }
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

    private void unLink(Node<E> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private void unLinkFirst(Node<E> node) {
        first = node.next;
        if (node.next == null) {
            last = null;
        } else {
            node.next.prev = null;
        }
        size--;
    }

    private void unLinkLast(Node<E> node) {
        last = node.prev;
        node.prev.next = null;
        size--;
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn`t exist");
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn`t exist");
        }
    }

    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void linkFirst(E value) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, value, f);
        first = newNode;
        if (f != null) {
            f.prev = newNode;
        } else {
            last = newNode;
        }
        size++;
    }

    private void linkLast(E value) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l != null) {
            l.next = newNode;
        } else {
            first = newNode;
        }
        size++;
    }
}
