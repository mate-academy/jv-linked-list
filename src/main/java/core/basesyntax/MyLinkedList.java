package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        return size == 0 ? addFirstValue(value) : addNotFirstValue(value);
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index passed to the method is invalid!");
        }
        if (size == index) {
            add(value);
        } else {
            insertByIndex(value, getNodeByIndex(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;        
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> oldElement = getNodeByIndex(index);
        unlink(oldElement);
        return oldElement.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> value = head;
        for (int i = 0; i < size; i++) {
            if (object == value.item || object != null && object.equals(value.item)) {
                unlink(value);
                return true;
            }
            value = value.next;
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

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev,T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void unlink(Node<T> value) {
        if (size != 1) {
            Node<T> beforeToElement = value.prev;
            Node<T> afterToElement = value.next;
            if (beforeToElement != null) {
                beforeToElement.next = afterToElement;
            } else {
                this.head = afterToElement;
                afterToElement.prev = null;
            }
            if (afterToElement != null) {
                afterToElement.prev = beforeToElement;
            } else {
                this.tail = beforeToElement;
                beforeToElement.next = null;
            }
        }
        size--;
    }

    private boolean addNotFirstValue(T value) {
        Node<T> tailValue = tail;
        Node<T> newElement = new Node<>(tailValue, value,null);
        tail = newElement;
        tailValue.next = newElement;
        size++;
        return true;
    }

    private boolean addFirstValue(T value) {
        Node<T> headValue = head;
        Node<T> newElement = new Node<>(null, value,headValue);
        head = newElement;
        tail = newElement;
        size++;
        return true;
    }

    private Node<T> getNodeByIndex(int index) {
        if (size / 2 > index) {
            Node<T> value = head;
            for (int i = 0; i < index; i++) {
                value = value.next;
            }
            return value;
        } else {
            Node<T> value = tail;
            for (int i = size - 1; i > index; i--) {
                value = value.prev;
            }
            return value;
        }
    }

    private void insertByIndex(T value, Node<T> element) {
        Node<T> beforeToElement = element.prev;
        Node<T> newElement = new Node<>(beforeToElement, value,element);
        if (beforeToElement != null) {
            beforeToElement.next = newElement;
        } else {
            this.head = newElement;
        }
        element.prev = newElement;
        size++;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index passed to the method is invalid!");
        }
    }
}
