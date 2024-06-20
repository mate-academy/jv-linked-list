package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (first == null) {
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
        checkIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            if (first == null) {
                first = newNode;
                last = newNode;
            } else {
                newNode.next = first;
                first.prev = newNode;
                first = newNode;
            }
        } else if (index == size) {
            if (last == null) {
                first = newNode;
                last = newNode;
            } else {
                newNode.prev = last;
                last.next = newNode;
                last = newNode;
            }
        } else {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            Node<T> prevNode = current.prev;
            newNode.next = current;
            newNode.prev = prevNode;
            prevNode.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexFits(index);
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return current.item;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndexFits(index);
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                T result = current.item;
                current.item = value;
                return result;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndexFits(index);
        Node<T> toRemove;
        if (index == 0) {
            toRemove = first;
            first = first.next;
            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
        } else if (index == size - 1) {
            toRemove = last;
            last = last.prev;
            if (last != null) {
                last.next = null;
            } else {
                first = null;
            }
        } else {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            toRemove = current;
            Node<T> prevNode = current.prev;
            Node<T> nextNode = current.next;

            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }

        size--;
        return toRemove.item;
    }

    @Override
    public boolean remove(T object) {
        if (first == null) {
            return false;
        }
        Node<T> current = first;
        while (current != null) {
            if ((object == null && current.item == null)
                    || (object != null && object.equals(current.item))) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    first = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    last = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexFits(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
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
