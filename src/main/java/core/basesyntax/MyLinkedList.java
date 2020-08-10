package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    int size = 0;
    Node<T> first = null;
    Node<T> last = null;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {

        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
            return;
        }

        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;

    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Неправильный индекс");
        }
        Node<T> getNode = first;
        for (int i = 0; i < index; i++) {
            getNode = getNode.next;
        }
        return getNode;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T prevItem = node.item;
        node.item = value;
        return prevItem;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        removeNode(node);
        return node.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> getNode = first;
        for (int i = 0; i < size; i++) {
            if (isEquals(getNode, t)) {
                removeNode(getNode);
                return true;
            }
            getNode = getNode.next;
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

    private boolean isEquals(Node<T> node, T t) {

        return Objects.equals(node.item, t);
    }

    private void removeNode(Node<T> node) {
        if (node.next == null) {
            last = node.prev;
            size--;
            return;
        }
        if (node.prev == null) {
            first = node.next;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }
}
