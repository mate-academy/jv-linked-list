package core.basesyntax;

import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        final Node<T> oldLast = last;
        final Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            final Node<T> newPrev = node(index).prev;
            final Node<T> newNode = new Node<>(newPrev, value, node(index));
            node(index).prev = newNode;
            if (newPrev == null) {
                first = newNode;
            } else {
                newPrev.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        addAll(size, list);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        checkPositionIndex(index);
        Object[] arrayCollection = c.toArray();
        int lengthArrayCollection = arrayCollection.length;
        if (lengthArrayCollection == 0) {
            return false;
        }
        Node<T> newPrev;
        Node<T> newNext;
        if (index == size) {
            newNext = null;
            newPrev = last;
        } else {
            newNext = node(index);
            newPrev = newNext.prev;
        }
        for (Object collectionExtendsT : arrayCollection) {
            @SuppressWarnings("unchecked") T collection = (T) collectionExtendsT;
            Node<T> newNode = new Node<>(newPrev, collection, null);
            if (newPrev == null) {
                first = newNode;
            } else {
                newPrev.next = newNode;
            }
            newPrev = newNode;
        }
        if (newNext == null) {
            last = newPrev;
        } else {
            newPrev.next = newNext;
            newNext.prev = newPrev;
        }
        size += lengthArrayCollection;
        return true;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> setNode = node(index);
        T oldVal = setNode.item;
        setNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    T unlink(Node<T> x) {
        final T element = x.item;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return element;
    }

    Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> newFirst = first;
            for (int i = 0; i < index; i++) {
                newFirst = newFirst.next;
            }
            return newFirst;
        } else {
            Node<T> newLast = last;
            for (int i = size - 1; i > index; i--) {
                newLast = newLast.prev;
            }
            return newLast;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    class Node<T> {
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


