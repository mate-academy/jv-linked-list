package core.basesyntax;

import java.util.List;

public class MyLinkedList<M> implements MyLinkedListInterface<M> {
    private Node<M> top;
    private Node<M> bottom;
    private int size;

    @Override
    public void add(M value) {
        if (size == 0) {
            linkTop(value);
        } else {
            linkBottom(value);
        }
    }

    @Override
    public void add(M value, int index) {
        checkPositionIndex(index);
        if (index == 0 || size == 0) {
            linkTop(value);
        } else if (index == size) {
            linkBottom(value);
        } else {
            Node<M> a = node(index);
            Node<M> newNode = new Node<>(a.prev, value, a);
            a.prev.next = newNode;
            a.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<M> list) {
        if (!(list == null)) {
            for (M item : list) {
                add(item);
            }
        }
    }

    @Override
    public M get(int index) {
        checkItemIndex(index);
        return node(index).item;
    }

    @Override
    public M set(M value, int index) {
        checkItemIndex(index);
        Node<M> a;
        if (index == 0) {
            a = top;
            unLinkTop(a);
            linkTop(value);
        } else {
            a = node(index);
            unLink(a);
            add(value, index);
        }
        return a.item;
    }

    @Override
    public M remove(int index) {
        checkItemIndex(index);
        if (index == 0) {
            Node<M> b = top;
            unLinkTop(b);
            return b.item;
        } else if (index == size - 1) {
            Node<M> c = bottom;
            unLinkBottom(c);
            return c.item;
        } else {
            Node<M> a = node(index);
            unLink(a);
            return a.item;
        }
    }

    @Override
    public boolean remove(M object) {
        Node<M> b = top;
        while (b != null) {
            if (object == null && b.item == null) {
                unLink(b);
                return true;
            } else if (object != null && object.equals(b.item)) {
                if (size == 1 || object.equals(top.item)) {
                    unLinkTop(b);
                } else {
                    unLink(b);
                }
                return true;
            }
            b = b.next;
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

    private static class Node<M> {
        private final M item;
        private Node<M> next;
        private Node<M> prev;

        public Node(Node<M> prev, M item, Node<M> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<M> node(int index) {
        Node<M> a;
        if (index < (size / 2)) {
            a = top;
            for (int i = 0; i < index; i++) {
                a = a.next;
            }
        } else {
            a = bottom;
            for (int i = size - 1; i > index; i--) {
                a = a.prev;
            }
        }
        return a;
    }

    private void linkTop(M value) {
        Node<M> b = top;
        Node<M> newNode = new Node<>(null, value, b);
        top = newNode;
        if (b != null) {
            b.prev = newNode;
        } else {
            bottom = newNode;
        }
        size++;
    }

    private void linkBottom(M value) {
        Node<M> c = bottom;
        Node<M> newNode = new Node<>(c, value, null);
        bottom = newNode;
        if (c != null) {
            c.next = newNode;
        } else {
            top = newNode;
        }
        size++;
    }

    private void unLink(Node<M> node) {
        if (!(node == null)) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
    }

    private void unLinkTop(Node<M> node) {
        if (!(node == null)) {
            top = node.next;
            if (node.next == null) {
                bottom = null;
            } else {
                node.next.prev = null;
            }
            size--;
        }
    }

    private void unLinkBottom(Node<M> node) {
        if (!(node == null)) {
            bottom = node.prev;
            node.prev.next = null;
            size--;
        }
    }

    private void checkItemIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
        }
    }
}
