package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> current = getNodeByIndex(index - 1);
            newNode.next = current.next;
            current.next = newNode;
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
        Objects.checkIndex(index, size);
        if (getNodeByIndex(index) == null) {
            throw new IndexOutOfBoundsException("bad index");
        } else {
            return getNodeByIndex(index).value;
        }
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T temp = node.value;
        node.value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node;
        for (node = first; node != null; node = node.next) {
            if (Objects.equals(object, node.value)) {
                unlink(node);
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
        if (first == null) {
            return true;
        } else {
            return false;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        T value = node.value;
        node.value = null;
        size--;
        return value;
    }
}
