package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
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

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        if (first == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> newNode = new Node<>(null,value, first);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index != size) {
            return getNodeByIndex(index).item;
        }
        throw new IndexOutOfBoundsException("You can't Get value from this index: " + index);
    }

    @Override
    public T set(T value, int index) {
        if (index != size) {
            T element = getNodeByIndex(index).item;
            Node<T> node = getNodeByIndex(index);
            node.item = value;
            return element;
        }
        throw new IndexOutOfBoundsException("You can't Set value to this index: " + index);
    }

    @Override
    public T remove(int index) {
        if (index != size && index >= 0) {
            T removedElement;
            if (index == 0) {
                removedElement = first.item;
                first = first.next;
                if (first == null) {
                    last = null;
                }
            } else {
                Node<T> prev = getNodeByIndex(index - 1);
                removedElement = prev.next.item;
                prev.next = prev.next.next;
                if (index == size - 1) {
                    last = prev;
                }
            }
            size--;
            return removedElement;
        }
        throw new IndexOutOfBoundsException("You can't Remove value from this index: " + index);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.item == null && object == null
                    || current.item != null && current.item.equals(object)) {
                remove(i);
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
        return size() == 0;
    }

    private void checkIndex(int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException("There is no element on this index: " + index);
        }
    }

}
