package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private T item;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    {
        Node<T> head = null;
        Node<T> tail = null;
    }

    public MyLinkedList(T item) {
        this.item = item;
    }

    public MyLinkedList(){
    }

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("exception");
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
            head.prev = null;
            tail.next = null;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
            tail.next = null;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("exception");
        }
        if (size > 0 && index < (size - 1) && index > 0) {
            Node<T> node = new Node<>(head, value, tail);
            for (int i = 1; i < index; i++) {
                node.prev = node.prev.next;
            }
            node.prev.next = node;
            for (int i = 1; i < size - index; i++) {
                node.next = node.next.prev;
            }
            node.next.prev = node;
            size++;
        }
        if ((size - 1) == index) {
            Node<T> node = new Node<>(null, value, null);
            node.prev = tail.prev;
            tail.prev = node;
            node.next = tail;
            node.prev.next = node;
            size++;
        }
        if (index == 0 && size > 0) {
            Node<T> node = new Node<>(null, value, null);
            head.prev = node;
            node.next = head;
            head = node;
            head.prev = null;
            size++;
        }
        if (index == size) {
            add(value);
        }
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
        if (size == 0) {
            return null;
        }
        if (index == 0) {
            return head.item;
        }
        if (index > 0) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.item;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = get(index);
        if (size > 0 && index < (size - 1) && index > 0) {
            Node<T> node = new Node<>(head,value,tail);
            for (int i = 1; i < index; i++) {
                node.prev = node.prev.next;
            }
            node.prev.next = node;
            for (int i = 1; i < size - index - 1; i++) {
                node.next = node.next.prev;
            }
            node.next.prev = node;
        }
        if (index == 0 && size > 0) {
            Node<T> node = new Node<>(null, value, head.next);
            head.next.prev = node;
            head = node;
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        final T removedValue = get(index);
        size--;
        if (size == 0) {
            head = null;
        }
        if (index == 0 && size > 0) {
            head = head.next;
            head.prev = null;
        }
        if (index == (size) && size > 0) {
            tail = tail.prev;
            tail.next = null;
        }
        if (index > 0 && index < (size)) {
            Node<T> nodeBeforeIndex = head;
            for (int i = 1; i < index; i++) {
                nodeBeforeIndex = nodeBeforeIndex.next;
            }
            nodeBeforeIndex.next = nodeBeforeIndex.next.next;
            Node<T> nodeAfterIndex = tail;
            for (int i = 1; i < size - index; i++) {
                nodeAfterIndex = nodeAfterIndex.prev;
            }
            nodeAfterIndex.prev = nodeAfterIndex.prev.prev;
        }
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                remove(i);
                return true;
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
}
