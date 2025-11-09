package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> previousLast = last;
        Node<T> newNode = new Node<>(previousLast, value, null);
        last = newNode;
        if (previousLast == null) {
            first = newNode;
        } else {
            previousLast.setNext(newNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> successor = findNodeByIndex(index);
        Node<T> predecessor = successor.getPrev();
        Node<T> newNode = new Node<>(predecessor, value, successor);
        successor.setPrev(newNode);
        if (predecessor == null) {
            first = newNode;
        } else {
            predecessor.setNext(newNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("The provided list is null");
        }
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return findNodeByIndex(index).getItem();
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.getItem();
        node.setItem(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (object == null ? current.getItem() == null
                    : object.equals(current.getItem())) {
                unlink(current);
                return true;
            }
            current = current.getNext();
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

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.getPrev();
            }
            return node;
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.getItem();
        final Node<T> prev = node.getPrev();
        final Node<T> next = node.getNext();

        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
        }

        if (next == null) {
            last = prev;
        } else {
            next.setPrev(prev);
        }

        // always clear removed node's links and item
        node.setItem(null);
        node.setPrev(null);
        node.setNext(null);

        size--;
        return element;
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }

        E getItem() {
            return item;
        }

        void setItem(E item) {
            this.item = item;
        }

        Node<E> getPrev() {
            return prev;
        }

        void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        Node<E> getNext() {
            return next;
        }

        void setNext(Node<E> next) {
            this.next = next;
        }
    }
}
