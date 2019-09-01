package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        first = new Node<>(null, null, null);
        last = new Node<>(null, null, null);
    }

    private void checkIndexException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void addToTail(T value) {
        Node<T> newElem = new Node<>(last, value, null);
        if (last.item == null) {
            first.next = newElem;
            newElem.prev = first;
        } else {
            last.next = newElem;
        }
        last = newElem;
        size++;
    }

    private void addToHead(T value) {
        Node<T> newElem = new Node<>(null, value, first);
        if (first.item == null) {
            last.prev = newElem;
        } else {
            first.prev = newElem;
        }
        first = newElem;
        size++;
    }

    @Override
    public void add(T value) {
        if (first.item == null) {
            addToHead(value);
        } else {
            addToTail(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            if (first.item == null) {
                addToHead(value);
            } else {
                addToTail(value);
            }
        } else {
            Node<T> result = first;
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    Node<T> newElem = new Node<>(result.prev, value, result.prev.next);
                    result.prev.next = newElem;
                    result.prev = newElem;
                    size++;
                    break;
                }
                result = result.next;
            }
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
        checkIndexException(index);
        Node<T> result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.item;
    }

    @Override
    public void set(T value, int index) {
        checkIndexException(index);
        Node<T> result = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                result.item = value;
            }
            result = result.next;
        }
    }

    @Override
    public T remove(int index) {
        checkIndexException(index);
        Node<T> result = first;
        for (int i = 0; i < size; i++) {
            if (index == 0) {
                result.next.prev = result.prev;
                first = result.next;
                break;
            } else if (index == i && i < size - 1) {
                result.prev.next = result.next;
                result.next.prev = result.prev;
                break;
            } else if (index == i && i == size - 1) {
                result.prev.next = result.next;
                last = result.prev;
                break;
            }
            result = result.next;
        }
        size--;
        return result.item;
    }

    @Override
    public T remove(T t) {
        Node<T> counterFirst = first;
        Node<T> counterLast = last;
        for (int i = 0; i < size / 2 + 1; i++) {
            if (counterFirst.item == t) {
                return remove(i);
            } else if (counterLast.item == t) {
                return remove(size - i - 1);
            }
            counterFirst = counterFirst.next;
            counterLast = counterLast.prev;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        private Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
