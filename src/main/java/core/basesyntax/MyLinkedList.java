package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addIfFirst(value);
        } else {
            addIfLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            addIfLast(value);
        } else {
            Node<T> standStill = searchNode(index).prev;
            Node<T> newNode = new Node<>(standStill, value, searchNode(index));
            searchNode(index).prev = newNode;
            if (standStill == null) {
                first = newNode;
            } else {
                standStill.next = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        T[] inputArray = (T[]) list.toArray();
        int lengthInputArray = inputArray.length;
        if (lengthInputArray == 0) {
            return;
        }
        Node<T> standStill = last;
        for (T element : inputArray) {
            Node<T> newNode = new Node<>(standStill, element, null);
            if (standStill == null) {
                first = newNode;
            } else {
                standStill.next = newNode;
            }
            standStill = newNode;
        }
        last = standStill;
        size += lengthInputArray;
    }

    @Override
    public T get(int index) {
        checkIndexForGetAndSet(index);
        return searchNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGetAndSet(index);
        Node<T> foundNode = searchNode(index);
        T oldValue = foundNode.item;
        foundNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForGetAndSet(index);
        return unlink(searchNode(index));
    }

    @Override
    public boolean remove(T value) {
        if (value == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (value.equals(node.item)) {
                    unlink(node);
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
        return size() == 0;
    }

    private void addIfFirst(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        first = newNode;
        last = newNode;
    }

    private void addIfLast(T value) {
        Node<T> previousListElement = last;
        Node<T> newNode = new Node<>(previousListElement, value, null);
        last = newNode;
        if (previousListElement == null) {
            first = newNode;
        } else {
            previousListElement.next = newNode;
        }
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("You used an incorrect index");
        }
    }

    private Node<T> searchNode(int index) {
        if (index > (size / 2)) {
            Node<T> searchIndex = last;
            for (int i = size - 1; i > index; i--) {
                searchIndex = searchIndex.prev;
            }
            return searchIndex;
        } else {
            Node<T> searchIndex = first;
            for (int i = 0; i < index; i++) {
                searchIndex = searchIndex.next;
            }
            return searchIndex;
        }
    }

    private void checkIndexForGetAndSet(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("You used an incorrect index. Element not found");
        }
    }

    private T unlink(Node<T> searchNode) {
        final T element = searchNode.item;
        Node<T> next = searchNode.next;
        Node<T> previous = searchNode.prev;
        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
            searchNode.next = null;
        }
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            searchNode.prev = null;
        }
        searchNode.item = null;
        size--;
        return element;
    }
}
