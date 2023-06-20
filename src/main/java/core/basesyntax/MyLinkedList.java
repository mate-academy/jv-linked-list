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
        checkIndex(index);
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
        checkIndex(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Wrong index " + index + "! "
                    + "Index value must be less than size");
        }
        return getNode(index).item;

    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Wrong index " + index + "! "
                    + "Index value must be less than size");
        }
        T valueToReturn = getNode(index).item;
        getNode(index).item = value;
        return valueToReturn;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Wrong index " + index + "! "
                    + "Index value must be less than size");
        }
        Node<T> elementToReturn = getNode(index);
        unlink(elementToReturn);
        size--;
        return elementToReturn.item;
    }

    @Override
    public boolean remove(T object) {
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

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Wrong index " + index + "! "
                    + "Index must not be the negative digit");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("Wrong index " + index + "! "
                    + "Index value must be less than size");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current = null;
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

    private void linkBefore(T e, Node<T> succ) {
        Node<T> pred = succ.prev;
        Node<T> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
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
        if (node == first ) {
            first = next;
        }
        if (node == last) {
            last = prev;
        } else {
            // TODO finish implementation
            next.prev = prev;
            prev.next = next;
        }

    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> linkedList = new MyLinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.set(223, 1);

        System.out.println(linkedList.get(1));
    }
}
