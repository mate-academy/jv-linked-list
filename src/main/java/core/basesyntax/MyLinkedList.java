package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> head;
    private MyNode<T> tail;

    @Override
    public void add(T value) {
        MyNode<T> node = new MyNode<>(null, value, null);
        if (head == null) {
            head = node;
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkForNegativeIndex(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            addInTheMiddle(value, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        MyNode<T> currentNode = getNodeByIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        MyNode<T> currentNode = getNodeByIndex(index);
        T oldVal = currentNode.item;
        currentNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        MyNode<T> currentNode = getNodeByIndex(index);
        return unlink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((object == null && currentNode.item == null)
                    || (currentNode != null
                    && currentNode.item.equals(object))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private MyNode<T> getNodeByIndex(int index) {
        if (index < (size >> 1)) {
            MyNode<T> currentNode = head;
            for (int i = 0; i < index && currentNode.next != null; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            MyNode<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private T unlink(MyNode<T> node) {
        if (node == null) {
            size--;
            return null;
        }
        final T element = node.item;
        MyNode<T> next = node.next;
        MyNode<T> prev = node.prev;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index doesn't exist");
        }
    }

    private void checkForNegativeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index doesn't exist");
        }
    }

    private void addFirst(T value) {
        MyNode<T> node = new MyNode<>(null, value, null);
        node.next = head;
        head = node;
    }

    private void addLast(T value) {
        MyNode<T> node = new MyNode<>(null, value, null);
        MyNode<T> currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }
        currentNode.next = node;
        node.prev = currentNode;
        tail = node;
    }

    private void addInTheMiddle(T value, int index) {
        MyNode<T> node = new MyNode<>(null, value, null);
        MyNode<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.next;
        }
        currentNode.next.prev = node;
        node.next = currentNode.next;
        node.prev = currentNode;
        currentNode.next = node;
    }

    private static class MyNode<E> {
        private MyNode<E> next;
        private E item;
        private MyNode<E> prev;

        public MyNode(MyNode<E> next, E item, MyNode<E> prev) {
            this.next = next;
            this.item = item;
            this.prev = prev;
        }
    }
}
