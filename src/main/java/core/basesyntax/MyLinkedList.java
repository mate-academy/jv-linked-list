package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;
    private class Node<T> {
        public T value;
        public Node<T> prev;
        public Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public MyLinkedList() {}

    private void insertFirst(T value) {
            Node<T> node = new Node<>(value);
            node.prev = null;
            node.next = null;
            head = node;
            tail = node;
            size++;
    }

    private void insertLast(T value) {
        Node<T> node = new Node<>(value);
        tail.setNext(node);
        node.setPrev(tail);
        node.setNext(null);
        tail = node;
        size++;
    }
    private void insertHead(T value) {
        Node<T> node = new Node<>(value);
        Node<T> nextNode = head;
        node.setPrev(null);
        node.setNext(nextNode);
        nextNode.setPrev(node);
        head = node;
        size++;
    }

    private void insert(Node<T> tNode, T value) {
        Node<T> previousNode = tNode.getPrev();
        Node<T> node = new Node<>(value);
        previousNode.next = node;
        node.prev = previousNode;
        node.next = tNode;
        tNode.prev = node;
        size++;
    }

    private Node<T> iterator(int index) {
        if (index < size - index) {
            return headIterator(index);
        } else {
            return tailIterator(index);
        }
    }

    private Node<T> headIterator(int index) {
        Node<T> tNode = head;
        for (int i = 0; i < index; i++) {
            tNode = tNode.getNext();
        }
        return tNode;
    }

    private Node<T> tailIterator(int index) {
        Node<T> tNode = tail;
        for (int i = 0; i < size - index - 1; i++) {
            tNode = tNode.getPrev();
        }
        return tNode;
    }
    public void unlink(Node node) {
        Node previous = node.getPrev();
        Node next = node.getNext();
        if (previous == null && size != 1) {
            next.prev = null;
            head = next;
        } else if (next == null && size != 1) {
            previous.next = null;
            tail = previous;
        } else if (size == 1) {
            head.prev = null;
            head.next = null;
            head.value = null;
        } else {
            previous.next = next;
            next.prev = previous;
        }
    }
    private void indexCheck(int index) {
        if (index >= size || index < 0) throw new ArrayIndexOutOfBoundsException("Invalid index");
    }

    private void indexCheckAdd(int index) {
        if (index > size || index < 0) throw new ArrayIndexOutOfBoundsException("Invalid index");
    }

    private void addByIndex(int index, T value) {
        if (index == size && size != 0) insertLast(value);
        else if (index == 0 && size != 0) insertHead(value);
        else if (index == 0 && size == 0) insertFirst(value);
        else insert(iterator(index), value);
    }

    public boolean removeByValue(T object) {
        Node<T> tNode = head;
        for (int i = 0; i < size; i++) {
            if (tNode.getValue() == object || tNode.getValue() != null && tNode.getValue().equals(object)) {
                unlink(tNode);
                size--;
                return true;
            } else {
                tNode = tNode.getNext();
            }
        }
        return false;
    }

    private T removeByIndex(int index) {
        Node old = iterator(index);
        indexCheck(index);
        unlink(old);
        size--;
        return (T)old.getValue();
    }

    private T setByIndex(int index, T value) {
        indexCheck(index);
        Node<T> tNode = iterator(index);
        T oldValue = tNode.getValue();
        tNode.setValue(value);
        return oldValue;
    }
    @Override
    public void add(T value) {
        if (isEmpty()) insertFirst(value);
        else insertLast(value);
    }

    @Override
    public void add(T value, int index) {
        indexCheckAdd(index);
        addByIndex(index, value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return iterator(index).value;
    }

    @Override
    public T set(T value, int index) {
        return setByIndex(index, value);
    }

    @Override
    public T remove(int index) {
        return removeByIndex(index);
    }

    @Override
    public boolean remove(T object) {
        return removeByValue(object);
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
