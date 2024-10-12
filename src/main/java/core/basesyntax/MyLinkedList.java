package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

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
            first = new Node<>(null, value, null);
            last = first;
        } else {
            last.next = new Node<>(last, value, null);
            last.next.prev = last;
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            checkIndexValidity(index);
        }
        if (index == 0 && size > 0) {
            addToTheBeginning(value);
        }
        if (index == size) {
            addToTheEnd(value);
        } else if (index > 0) {
            addToTheMiddle(value, index);
        }
    }

    private void addToTheMiddle(T value, int index) {
        int i = 0;
        for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
            if (i == index) {
                Node<T> newNode = new Node<>(x.prev, value, x);
                x.prev.next = newNode;
                x.prev = newNode;
                break;
            }
            i++;
        }
        size++;
    }

    private void addToTheEnd(T value) {
        add(value);
    }

    private void addToTheBeginning(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndexValidity(index);
        int i = 0;
        for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
            if (i == index) {
                return x.item;
            }
            i++;
        }
        return null;
    }

    private void checkIndexValidity(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndexValidity(index);
        int i = 0;
        for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
            if (i == index) {
                T oldValue = x.item;
                x.item = value;
                return oldValue;
            }
            i++;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndexValidity(index);
        int i = 0;
        for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
            if (i == index) {
                T removedItem = x.item;
                remove(x.item);
                return removedItem;
            }
            i++;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    void unlink(Node<T> x) {
        final MyLinkedList.Node<T> next = x.next;
        final MyLinkedList.Node<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
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
