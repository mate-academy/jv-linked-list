package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = last = new Node<>(null, value, null);
        } else {
            last = new Node<>(last, value, null);
            last.prev.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            first = new Node<>(null, value, first);
            first.next.prev = first;
            size++;
        } else {
            checkIndex(index);
            Node<T> current = findNode(index);
            current.prev = new Node<>(current.prev, value, current);
            current.prev.prev.next = current.prev;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T temporaryItem = findNode(index).item;
        findNode(index).item = value;
        return temporaryItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> temporaryNode = findNode(index);
        if (size == 1) {
            first = last = null;
        } else if (index == 0) {
            first = first.next;
            first.prev = null;
        } else if (index == size - 1) {
            last = last.prev;
            last.next = null;
        } else {
            temporaryNode.prev.next = temporaryNode.next;
            temporaryNode.next.prev = temporaryNode.prev;
        }
        size--;
        return temporaryNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((get(i) == object) || (get(i) != null) && (get(i).equals(object))) {
                remove(i);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Your index " + index
                        + " is over the size " + size);
        }
    }

    private Node<T> findNode(int index) {
        if (index <= (size / 2)) {
            Node<T> currentNode = first;
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    return currentNode;
                } else {
                    currentNode = currentNode.next;
                }
            }
        } else {
            Node<T> currentNode = last;
            for (int i = size - 1; i >= index; i--) {
                if (i == index) {
                    return currentNode;
                } else {
                    currentNode = currentNode.prev;
                }
            }
        }
        return null;
    }
}
