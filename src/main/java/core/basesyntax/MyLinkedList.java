package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        last = (size == 0) ? null : last;
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        }
        if (size > 0) {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        Node<T> newNode;
        if (index == 0) {
            Node<T> n = getNodeByIndex(index);
            newNode = new Node<>(null, value, n);
            if (size > 0) {
                n.prev = newNode;
            }
            first = newNode;
            if (size == 0) {
                last = newNode;
            }
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> n = getNodeByIndex(index);
        newNode = new Node<>(n.prev, value, n);
        n.prev.next = (size == 0) ? null : newNode;
        n.prev = (size == 0) ? null : newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndexGet(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndexGet(index);
        var n = getNodeByIndex(index).item;
        remove(index);
        add(value,index);
        return n;
    }

    @Override
    public T remove(int index) {
        checkPositionIndexGet(index);
        Node<T> removeResult = getNodeByIndex(index);
        if (index == 0) {
            return unlinkFirst(removeResult);
        }
        if (index == size) {
            return unlinkLast(removeResult);
        }
        return unlink(removeResult);
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (first == null);
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

    private Node<T> getNodeByIndex(int index) {
        checkPositionIndex(index);
        if (index < (size >> 1)) {
            var x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            var x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkPositionIndexGet(int index) {
        if (!isPositionIndexGet(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private boolean isPositionIndexGet(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private T unlinkFirst(MyLinkedList.Node<T> first) {
        final T element = first.item;
        final var next = first.next;
        first.item = null;
        first.next = null;
        this.first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        return element;
    }

    private T unlinkLast(MyLinkedList.Node<T> last) {
        final T element = last.item;
        final var prev = last.prev;
        last.item = null;
        last.prev = null;
        this.last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return element;
    }

    private T unlink(MyLinkedList.Node<T> x) {
        final T element = x.item;
        final var next = x.next;
        final var prev = x.prev;
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
        return element;
    }
}
