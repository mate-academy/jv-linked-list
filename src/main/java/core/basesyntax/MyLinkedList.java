package core.basesyntax;

import java.util.List;
import org.w3c.dom.Node;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    static public class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            first = last = new Node<>(value, null, null);
        } else {
            Node<T> newNode = new Node<>(value, last, null);
            last.next = newNode;
            last = newNode;
        }
        ++size;
    }

    @Override
    public void add(T value, int index) {
        if (index > 0 && index < size) {
            Node<T> searched = searchByIndex(index);
            Node<T> newNode = new Node<>(value, searched.prev, searched);
            searched.prev.next = newNode;
            searched.prev = newNode;
            ++size;
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value, null, first);
            first.prev = newNode;
            first = newNode;
            ++size;
        } else {
            checkIndex(index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T t: list) {
                add(t);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = searchByIndex(index);
        T oldValue = (T) newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        unlink(Node<T> node);

        return null;
    }

    @Override
    public boolean remove(T object) {
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

    private Node<T> searchByIndex(int index) {
        Node<T> node;
        if (index <= size / 2) {
            node = (Node<T>) this.first;
            for (int i = 0; i < index; node = node.next, ++i);
        } else {
            node = (Node<T>) this.last;
            for (int i = size - 1; i > index ; node = node.prev , --i);
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Size " + size + "! Index " + index + " wrong");
        }
    }

    private void unlink(Node<T> node) {
        node.item = null;
        node.prev = node.next = null;
    }
}
