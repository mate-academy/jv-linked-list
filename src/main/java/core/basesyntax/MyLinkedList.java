package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int SETTING = 0;
    private static final int NOT_SETTING = 1;
    private int size = 0;
    private transient Node<T> first;
    private transient Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev,T element,Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.item = element;
        }

    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index, NOT_SETTING);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index, SETTING);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index, SETTING);
        Node<T> node = node(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index, SETTING);
        Node<T> removedNode = node(index);
        if (index == 0) {
            first = first.next;
            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
        } else if (index == size - 1) {
            last = last.prev;
            if (last != null) {
                last.next = null;
            } else {
                first = null;
            }
        } else {
            unlink(removedNode);
        }
        size--;
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> itemNode = first; itemNode != null; itemNode = itemNode.next) {
            if ((itemNode.item == null && object == null)
                    || (itemNode.item != null && itemNode.item.equals(object))) {
                if (itemNode == first) {
                    first = first.next;
                    if (first != null) {
                        first.prev = null;
                    } else {
                        last = null;
                    }
                } else if (itemNode == last) {
                    last = last.prev;
                    if (last != null) {
                        last.next = null;
                    } else {
                        first = null;
                    }
                } else {
                    unlink(itemNode);
                }
                size--;
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
        if (first == null && last == null) {
            return true;
        }
        return size == 0;
    }

    private void linkLast(T element) {
        final Node<T> prev = last;
        final Node<T> newNode = new Node<>(prev,element,null);
        last = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private void checkPositionIndex(int index, int expressionToCheck) {
        if (expressionToCheck == NOT_SETTING) {
            if (!isPositionIndex(index)) {
                throw new IndexOutOfBoundsException(createIndexOutOfBoundMessage(index));
            }
        } else {
            if (!isPositionIndexToSet(index)) {
                throw new IndexOutOfBoundsException(createIndexOutOfBoundMessage(index));
            }
        }
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void linkBefore(T value, Node<T> node) {
        if (node != null) {
            final Node<T> pred = node.prev;
            final Node<T> newNode = new Node<>(pred, value, node);
            node.prev = newNode;
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    private void unlink(Node<T> node) {
        Node<T> pred = node.prev;
        Node<T> next = node.next;
        pred.next = next;
        next.prev = pred;
    }

    private String createIndexOutOfBoundMessage(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private boolean isPositionIndexToSet(int index) {
        return index >= 0 && index < size;
    }

}
