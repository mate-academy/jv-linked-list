package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private MyNode<T> head;
    private MyNode<T> tail;

    @Override
    public void add(T value) {
        MyNode<T> node = new MyNode<>(null, value, null);
        if (head == null) {
            head = node;
        } else {
            MyNode<T> currentNode = head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = node;
            node.prev = currentNode;
            tail = node;
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
        if (list != null) {
            for (T t : list) {
                add(t);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (head == null) {
            return null;
        } else {
            MyNode<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.item;
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        return null;
    }

    @Override
    public T remove(int index) {
        checkForNegativeIndex(index);
        T result = null;
        if (index == 0) {
            result = head.item;
            head = head.next;
            head.prev = null;
            size--;
        } else if (index == size) {
            result = tail.item;
            tail = tail.prev;
            tail.next = null;
            size--;
        } else {
            MyNode<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
            result = currentNode.item;
            size--;
        }
        return result;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }
        if (head.item == object) {
            head = head.next;
            head.prev = null;
            size--;
            return true;
        }
        MyNode currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.next.item == object) {
                currentNode.next = currentNode.next.next;
                size--;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkForNegativeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
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
        MyNode<E> next;
        E item;
        MyNode<E> prev;

        public MyNode(MyNode<E> next, E item, MyNode<E> prev) {
            this.next = next;
            this.item = item;
            this.prev = prev;
        }
    }
}
