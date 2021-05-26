package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private MyLinkedList.Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        MyLinkedList.Node<T> oldNode = findNodeByIndex(index);
        MyLinkedList.Node<T> oldNodePrev = oldNode.prev;
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(oldNode.prev, value, oldNode);
        oldNode.prev = newNode;
        if (oldNodePrev == null) {
            head = newNode;
        } else {
            oldNodePrev.next = newNode;
        }
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
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        MyLinkedList.Node<T> node = findNodeByIndex(index);
        T oldVal = node.item;
        node.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (object == node.item
                    || (object != null && object.equals(node.item))) {
                T deletedItem = unlink(node);
                boolean result = ((deletedItem == object)
                        || (object.equals(deletedItem)));
                return result;
            }
            node = node.next;
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

    private T unlink(MyLinkedList.Node<T> node) {
        final T element = node.item;
        MyLinkedList.Node<T> next = node.next;
        MyLinkedList.Node<T> prev = node.prev;

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

    private MyLinkedList.Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (index < (size / 2)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong Index");
        }
    }

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
}
