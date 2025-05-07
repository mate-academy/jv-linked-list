package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        } else {
            Node<T> current = findByIndex(index - 1);
            newNode.next = current.next;
            newNode.prev = current.prev;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> findNode = findByIndex(index);
        return findNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> findNode = findByIndex(index);
        T retunedValue = findNode.item;
        findNode.item = value;
        return retunedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(index);
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = new Node<>(null, object, null);
        int temp = 0;
        for (Node<T> i = first; i != null; i = i.next) {
            temp++;
            if (removedNode.item == i.item || removedNode.item != null && removedNode.item
                    .equals(i.item)) {
                unlink(temp - 1);
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

    private T unlink(int index) {
        T removedItem;
        if (index == 0) {
            removedItem = first.item;
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> removedNode = findByIndex(index);
            removedItem = removedNode.item;
            if (index == size - 1) {
                last = removedNode.prev;
            } else {
                removedNode.prev.next = removedNode.next;
                removedNode.next.prev = removedNode.prev;
            }
        }
        size--;
        return removedItem;
    }

    private Node<T> findByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndexAdd(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Incorrect index.");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException("Incorrect index.");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> next, T item, Node<T> prev) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
