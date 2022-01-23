package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value,null);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;

        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value,null);
        if (size == index) {
            add(newNode.item);
        } else {
            Node<T> shiftableNode = getNodeByIndex(index);
            if (shiftableNode.prev == null) {
                newNode.next = shiftableNode;
                shiftableNode.prev = newNode;
                first = newNode;
                size++;
            } else {
                shiftableNode.prev.next = newNode;
                newNode.prev = shiftableNode.prev;
                shiftableNode.prev = newNode;
                newNode.next = shiftableNode;
                size++;
            }
        }

    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
    }

    @Override
    public T get(int index) {

        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> resultNode = getNodeByIndex(index);
        T result = resultNode.item;
        resultNode.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(" index error ");
        } else {
            Node<T> removNode = getNodeByIndex(index);
            T result = removNode.item;
            unlink(removNode);
            size--;
            return result;
        }
    }

    @Override
    public boolean remove(T object) {

        Node<T> desired;
        for (desired = first; desired != null; desired = desired.next) {
            if (desired.item == null && object == null || desired.item.equals(object)) {
                unlink(desired);
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
        return size == 0;
    }

    private boolean firstOrLast(int index, int size) {
        return index < size / 2;
    }

    private Node<T> getNodeByIndex(int index) {
        int i = 0;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(" index error ");
        } else {
            Node<T> desired;
            if (firstOrLast(index, size)) {
                for (desired = first; desired != null; desired = desired.next) {
                    if (index == i) {
                        return desired;
                    }
                    i++;
                }
            } else {
                i = size - 1;
                for (desired = last; desired != null; desired = desired.prev) {
                    if (index == i) {
                        return desired;
                    }
                    i--;
                }
            }
        }

        return null;
    }

    private void unlink(Node<T> removtable) {
        if (removtable.prev != null || removtable.next != null) {
            if (removtable.next != null) {
                removtable.next.prev = removtable.prev;
            } else {
                removtable.prev.next = null;
                last = removtable.prev;
            }
            if (removtable.prev != null) {
                removtable.prev.next = removtable.next;
            } else {
                removtable.next.prev = null;
                first = removtable.next;
            }
        }
    }

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

}
