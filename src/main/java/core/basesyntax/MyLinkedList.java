package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> element = new Node<>(null,value,null);
            first = element;
            last = element;
        } else {
            Node<T> element = new Node<>(last,value,null);
            last.next = element;
            last = element;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index!");
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> element = new Node<>(null,value,first);
            first.prev = element;
            first = element;
            size++;
        } else {
            Node<T> currentNode = first;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
            Node<T> element = new Node<>(currentNode.prev,value,currentNode);
            currentNode.prev.next = element;
            currentNode.prev = element;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T value : list) {
                add(value);
            }
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index!");
        }
        Node<T> currentNode = first;
        for (int i = 1; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index!");
        }
        Node<T> currentNode = first;
        for (int i = 1; i <= index; i++) {
            currentNode = currentNode.next;
        }
        T oldItem = currentNode.item;
        currentNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index!");
        } else if (index == 0) {
            T value = first.item;
            if (first.next != null) {
                first.next.prev = null;
                Node<T> newFirst = first.next;
                first.next = null;
                first.item = null;
                first = newFirst;
            } else {
                first.next = null;
                first.item = null;
                first = null;
            }
            size--;
            return value;
        } else if (index == size - 1) {
            size--;
            last.prev.next = null;
            Node<T> newLast = last.prev;
            last.prev = null;
            T value = last.item;
            last.item = null;
            last = newLast;
            return value;
        } else {
            Node<T> currentNode = first;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
            size--;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            currentNode.prev = null;
            currentNode.next = null;
            T value = currentNode.item;
            currentNode.item = null;
            return value;
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                currentNode = currentNode.next;
            }
            if (object == currentNode.item
                    || (currentNode.item != null && currentNode.item.equals(object))) {
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
