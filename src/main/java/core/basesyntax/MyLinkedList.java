package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node(null, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("This index does not exist");
        }
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node(null, value, first);
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
            size++;
        } else {
            Node<T> buffer = getNodeByIndex(index);
            Node<T> newNode = new Node(buffer.prev, value, buffer);
            buffer.prev.next = newNode;
            buffer.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T obj : list) {
            add(obj);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        T buffer = getNodeByIndex(index).item;
        if (size == 1) {
            first.item = null;
        } else if (index == 0) {
            first = first.next;
            first.prev = null;
        } else if (index == size - 1) {
            last.prev.next = null;
        } else {
            Node<T> deletedNode = getNodeByIndex(index);
            deletedNode.next.prev = deletedNode.prev;
            deletedNode.prev.next = deletedNode.next;
        }
        size--;
        return buffer;
    }

    @Override
    public boolean remove(T object) {
        Node<T> bufferNode = first;
        int index = 0;
        while (!((bufferNode.item == object)
                || (object != null && object.equals(bufferNode.item)))) {
            if (bufferNode.next == null) {
                return false;
            }
            bufferNode = bufferNode.next;
            index++;
        }
        if (size == 1) {
            first.item = null;
        } else if (index == 0) {
            first = first.next;
            first.prev = null;
        } else if (index == size - 1) {
            last.prev.next = null;
        } else {
            Node<T> deletedNode = getNodeByIndex(index);
            deletedNode.next.prev = deletedNode.prev;
            deletedNode.prev.next = deletedNode.next;
        }
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> bufferNode = first;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                bufferNode = bufferNode.next;
            }
        } else {
            for (int j = size; j > size - index; j--) {
                bufferNode = bufferNode.next;
            }
        }
        return bufferNode;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("This index does not exist");
        }
    }
}
