package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        linkToTheEnd(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        if (size == index) {
            linkToTheEnd(value);
        } else {
            linkToTheMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        addAllByIndex(size, list);
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = nodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> itemToRemove = nodeByIndex(index);
        T oldValue = itemToRemove.value;
        unlink(itemToRemove);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = head; i != null; i = i.next) {
                if (i.value == null) {
                    unlink(i);
                    return true;
                }
            }
        } else {
            for (Node<T> i = head; i != null; i = i.next) {
                if (object.equals(i.value)) {
                    unlink(i);
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

    public void addAllByIndex(int index, List<T> list) {
        checkIndexToAdd(index);
        T[] array = (T[]) list.toArray();
        final int lengthOfList = array.length;
        Node<T> previous;
        Node<T> successive;
        if (index == size) {
            successive = null;
            previous = tail;
        } else {
            successive = nodeByIndex(index);
            previous = successive.next;
        }

        for (T item : array) {
            Node<T> newOne = new Node<>(previous, item, null);
            if (previous == null) {
                head = newOne;
            } else {
                previous.next = newOne;
            }
            previous = newOne;
        }

        if (successive == null) {
            tail = previous;
        } else {
            successive.prev = previous;
            previous.next = successive;
        }
        size += lengthOfList;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bound");
        }
    }

    private void checkIndexToAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bound to Add");
        }
    }

    private Node<T> nodeByIndex(int index) {
        if (index > size / 2) {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }

    private void linkToTheEnd(T value) {
        Node<T> h = tail;
        Node<T> newOne = new Node<>(h, value, null);
        tail = newOne;
        if (h == null) {
            head = newOne;
        } else {
            h.next = newOne;
        }
        size++;
    }

    private void linkToTheMiddle(T value, int index) {
        Node<T> successive = nodeByIndex(index);
        Node<T> previous = successive.prev;
        Node<T> newOne = new Node<>(previous, value, successive);
        successive.prev = newOne;
        if (previous == null) {
            head = newOne;
        } else {
            previous.next = newOne;
        }
        size++;
    }

    private void unlink(Node<T> itemToRemove) {
        Node<T> previous = itemToRemove.prev;
        Node<T> successive = itemToRemove.next;
        if (previous == null) {
            head = successive;
        } else {
            previous.next = successive;
            itemToRemove.prev = null;
        }

        if (successive == null) {
            tail = previous;
        } else {
            successive.prev = previous;
            itemToRemove.next = null;
        }

        itemToRemove.value = null;
        size--;
    }
}


