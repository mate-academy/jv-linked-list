package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, true);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
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
        checkIndex(index, false);
        return getNode(index).item;

    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, false);
        T valueToReturn = getNode(index).item;
        getNode(index).item = value;
        return valueToReturn;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> elementToReturn = getNode(index);
        unlink(elementToReturn);
        return elementToReturn.item;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> temp = first; temp != null; temp = temp.next) {
                if (temp.item == null) {
                    unlink(temp);
                    return true;
                }
            }
        } else {
            for (Node<T> temp = first; temp != null; temp = temp.next) {
                if (temp.item != null && temp.item.equals(object)) {
                    unlink(temp);
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

    private void linkLast(T e) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index, boolean isAddingOperation) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Wrong index " + index + "! "
                    + "Index must not be the negative digit");
        }
        if (isAddingOperation && index > size || !isAddingOperation && index >= size) {
            throw new IndexOutOfBoundsException("Wrong index " + index + "! "
                    + "Index value must be less than size");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index <= size / 2) {
            current = first;
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    return current;
                }
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > 0; i--) {
                if (index == i) {
                    return current;
                }
                current = current.prev;
            }
        }
        return null;
    }

    private void linkBefore(T e, Node<T> node) {
        Node<T> pred = node.prev;
        Node<T> newNode = new Node<>(pred, e, node);
        node.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (node == first) {
            first = next;
        }
        if (node == last) {
            last = prev;
        } else {
            if (next != null && prev != null) {
                next.prev = prev;
                prev.next = next;
            } else if (prev != null) {
                prev.next = null;
            } else if (next != null) {
                next.prev = null;
            }
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
