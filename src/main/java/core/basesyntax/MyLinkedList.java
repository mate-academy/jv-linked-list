package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>();
        newNode.item = value;

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>();
        newNode.item = value;
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        if (index < size / 2) {
            Node<T> currentItem = head;
            for (int i = 0; i < index; i++) {
                currentItem = currentItem.next;
            }
            newNode.prev = currentItem.prev;
            newNode.next = currentItem;
            currentItem.prev.next = newNode;
            currentItem.prev = newNode;
        } else {
            Node<T> currentItem = tail;
            for (int i = size - 1; i > index; i--) {
                currentItem = currentItem.prev;
            }
            newNode.prev = currentItem.prev;
            newNode.next = currentItem;
            currentItem.prev.next = newNode;
            currentItem.prev = newNode;
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
        if (index == 0) {
            return head.item;
        } else if (index == size - 1) {
            return tail.item;
        } else {
            Node<T> currentItem = head;
            if (index < size / 2) {
                for (int i = 0; i < index; i++) {
                    currentItem = currentItem.next;
                }
                return currentItem.item;
            } else {
                currentItem = tail;
                for (int i = size - 1; i > index; i--) {
                    currentItem = currentItem.prev;
                }
                return currentItem.item;
            }
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentItem;
        if (index < size / 2) {
            currentItem = head;
            for (int i = 0; i < index; i++) {
                currentItem = currentItem.next;
            }
        } else {
            currentItem = tail;
            for (int i = size - 1; i > index; i--) {
                currentItem = currentItem.prev;
            }
        }
        T oldValue = currentItem.item;
        currentItem.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentItem;

        if (size == 1) {
            currentItem = head;
            head = null;
            tail = null;
            size--;
            return currentItem.item;
        }
        if (index == 0) {
            currentItem = head;
            head = head.next;
            head.prev = null;
            size--;
            return currentItem.item;
        } else if (index == size - 1) {
            currentItem = tail;
            tail = tail.prev;
            tail.next = null;
            size--;
            return currentItem.item;
        } else {
            if (index < size / 2) {
                currentItem = head;
                for (int i = 0; i < index; i++) {
                    currentItem = currentItem.next;
                }
                currentItem.prev.next = currentItem.next;
                currentItem.next.prev = currentItem.prev;
                size--;
                return currentItem.item;
            } else {
                currentItem = tail;
                for (int i = size - 1; i > index; i--) {
                    currentItem = currentItem.prev;
                }
                currentItem.prev.next = currentItem.next;
                currentItem.next.prev = currentItem.prev;
                size--;
                return currentItem.item;
            }
        }
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentItem = head; currentItem != null; currentItem = currentItem.next) {
            if (Objects.equals(currentItem.item, object)) {
                if (currentItem == head && currentItem == tail) {
                    head = null;
                    tail = null;
                } else if (currentItem == head) {
                    head = currentItem.next;
                    head.prev = null;
                } else if (currentItem == tail) {
                    tail = currentItem.prev;
                    tail.next = null;
                } else {
                    currentItem.prev.next = currentItem.next;
                    currentItem.next.prev = currentItem.prev;
                }
                size--;
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
        return  size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexForAdd(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
