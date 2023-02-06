package core.basesyntax;

import java.util.List;
import org.jetbrains.annotations.NotNull;

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
            Node<M> k = node(index);
            Node<M> newNode = new Node<>(k.prev, value, k);
            k.prev.next = newNode;
            k.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<M> list) {
        if (list != null) {
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
        Node<M> k = node(index);
        M updatedValue = k.item;
        k.item = value;
        return updatedValue;
    }

    @Override
    public M remove(int index) {
        checkItemIndex(index);
        return unLink(node(index));
    }

    @Override
    public boolean remove(M object) {
        for (Node<M> k = top; k != null; k = k.next) {
            if ((object == null && k.item == null)
                    || (object != null && object.equals(k.item))) {
                unLink(k);
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

    private static class Node<M> {
        private M item;
        private Node<M> next;
        private Node<M> prev;

        public Node(Node<M> prev, M item, Node<M> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkTop(M value) {
        Node<M> l = top;
        Node<M> newNode = new Node<>(null, value, l);
        top = newNode;
        if (l != null) {
            l.prev = newNode;
        } else {
            bottom = newNode;
        }
        size++;
    }

    private void linkBottom(M value) {
        Node<M> p = bottom;
        Node<M> newNode = new Node<>(p, value, null);
        bottom = newNode;
        if (p != null) {
            p.next = newNode;
        } else {
            top = newNode;
        }
        size++;
    }

    private M unLink(@NotNull Node<M> node) {
        final M removeItem = node.item;
        if (node.prev == null) {
            top = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            bottom = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return removeItem;
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

    private Node<M> node(int index) {
        Node<M> k;
        if (index < (size / 2)) {
            k = top;
            for (int i = 0; i < index; i++) {
                k = k.next;
            }
        } else {
            k = bottom;
            for (int i = size - 1; i > index; i--) {
                k = k.prev;
            }
        }
        return k;
    }
}
