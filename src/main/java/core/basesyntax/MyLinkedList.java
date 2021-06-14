package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> head;
    private MyNode<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            createFirstNode(value);
        } else {
            createLastNode(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            MyNode<T> newNode = new MyNode<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        checkIndex(index);
        MyNode<T> searchNode = searchNodeByIndex(index);
        MyNode<T> oldNode = searchNode.prev;
        MyNode<T> newNode = new MyNode<>(oldNode, value, searchNode);
        searchNode.prev = newNode;
        if (oldNode == null) {
            head = newNode;
        } else {
            oldNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        MyNode<T> searchNode = searchNodeByIndex(index);
        T oldElement = searchNode.item;
        searchNode.item = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(searchNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> removeNode = head;
        for (int i = 0; i < size; i++) {
            if (removeNode.item == object || (object != null && object.equals(removeNode.item))) {
                remove(i);
                return true;
            }
            removeNode = removeNode.next;
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

    private class MyNode<T> {
        private T item;
        private MyNode<T> next;
        private MyNode<T> prev;

        MyNode(MyNode<T> prev, T value, MyNode<T> next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is incorrect");
        }
    }

    private void createFirstNode(T value) {
        MyNode<T> firstNode = new MyNode<>(null, value, null);
        tail = firstNode;
        head = firstNode;
    }

    private void createLastNode(T value) {
        MyNode<T> lastNode = new MyNode<>(tail, value, null);
        if (head.next == null) {
            head.next = lastNode;
        } else {
            tail.next = lastNode;
        }
        tail = lastNode;
    }

    private MyNode<T> searchNodeByIndex(int index) {
        MyNode<T> searchNode;
        if (index < (size / 2)) {
            searchNode = head;
            for (int i = 0; i < index; i++) {
                searchNode = searchNode.next;
            }
            return searchNode;
        }
        searchNode = tail;
        for (int i = size - 1; i > index; i--) {
            searchNode = searchNode.prev;
        }
        return searchNode;
    }

    private T unlink(MyNode<T> node) {
        final T element = node.item;
        final MyNode<T> next = node.next;
        final MyNode<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }
}
