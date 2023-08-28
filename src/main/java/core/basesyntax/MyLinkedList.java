package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> prev;
        Node<T> next = null;
        prev = size == 0 ? null : tail;
        Node<T> newNode = new Node<>(prev, value, next);
        if (isEmpty()) {
            head = newNode;
        }
        if (size > 0) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == 0 || index == size) {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException("Index is incorrect!");
            }
            add(value);
            return;
        }
        Node<T> indexNode = findNodeByIndex(index);
        Node<T> beforeIndexNode = index == 0 ? null : indexNode.prev;
        Node<T> newNode = new Node<>(beforeIndexNode, value, indexNode);
        if (beforeIndexNode != null) {
            beforeIndexNode.next = newNode;
        } else {
            head = newNode;
        }
        indexNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            this.add(element);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> indexNode = findNodeByIndex(index);
        T oldValue = indexNode.item;
        indexNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> toRemove = findNodeByIndex(index);
        unlink(toRemove);
        return toRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> helperNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null && helperNode.item == null) {
                unlink(helperNode);
                return true;
            } else if (object != null && object.equals(helperNode.item)) {
                unlink(helperNode);
                return true;
            }
            helperNode = helperNode.next;
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

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> helperNode;
        if (index < size / 2) {
            helperNode = head;
            for (int i = 0; i < index; i++) {
                if (helperNode.next != null) {
                    helperNode = helperNode.next;
                }
            }
        } else {
            helperNode = tail;
            for (int i = size - 1; i > index; i--) {
                if (helperNode.prev != null) {
                    helperNode = helperNode.prev;
                }
            }
        }
        return helperNode;
    }

    private void unlink(Node<T> toRemove) {
        Node<T> prev = toRemove.prev;
        Node<T> next = toRemove.next;
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is incorrect!");
        }
    }
}
