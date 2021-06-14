package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> head;
    private MyNode<T> tail;
    private MyNode<T> prev;
    private MyNode<T> next;

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is incorrect");
        }
    }

    private void createFirstNode(T value) {
        MyNode<T> firstNode = new MyNode<>(prev, value, next);
        tail = firstNode;
        head = firstNode;
    }

    private void createLastNode(T value) {
        MyNode<T> lastNode = new MyNode<>(tail, value, next);
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

    private  T unlink(MyNode<T> node) {
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
        if (index == 0 && size == 0) {
            add(value);
            return;
        }
        if (index == 0 && head != null) {
            MyNode<T> newNode = new MyNode<>(prev, value, head);
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
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
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
        if (removeNode.item == object || (object != null && object.equals(removeNode.item))) {
            remove(0);
            return true;
        }
        for (int i = 1; i < size; i++) {
            removeNode = removeNode.next;

            if (removeNode.item == object || (object != null && object.equals(removeNode.item))) {
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
}
