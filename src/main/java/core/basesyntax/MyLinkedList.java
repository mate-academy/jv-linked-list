package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> temp = findNode(index);
        T oldItem = temp.item;
        temp.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = first;
        if (object == null) {
            while (temp != null) {
                if (temp.item == null) {
                    unlink(temp);
                    return true;
                }
                temp = temp.next;
            }
        } else {
            while (temp != null) {
                if (object.equals(temp.item)) {
                    unlink(temp);
                    return true;
                }
                temp = temp.next;
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkLast(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    private Node<T> findNode(int index) {
        checkElementIndex(index);
        if (index < (size >> 1)) {
            Node<T> temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp;
        } else {
            Node<T> temp = last;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
            return temp;
        }
    }

    private void linkBefore(T value, Node<T> node) {
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.prev == null) {
            first = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    private void checkElementIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("There is no element with this index");
        }
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        final T element = node.item;
        if (prev == null) {
            first = node.next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }
}
