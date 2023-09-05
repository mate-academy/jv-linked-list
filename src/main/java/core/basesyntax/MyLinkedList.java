package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.item = value;
            this.next = next;
        }

        public Node() {

        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        if (isEmpty() || index == size) {
            add(value);
        } else {
            Node<T> node = nodeByIndex(index);
            if (index == 0) {
                Node<T> newNode = new Node<>(null, value,first);
                first = newNode;
                first.next.prev = newNode;
                size++;
            } else {
                Node<T> newNode = new Node<>(node.prev, value,node);
                node.prev.next = newNode;
                node.prev = newNode;
                size++;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return nodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> node = nodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> node = nodeByIndex(index);
        T removeNode = node.item;
        delLink(node);
        return removeNode;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.item == object) {
                    delLink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    delLink(node);
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
        return size == 0;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>();
        newNode.item = value;
        last.next = newNode;
        newNode.prev = last;
        last = newNode;
        size++;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>();
        newNode.item = value;
        first = newNode;
        last = newNode;
        size++;
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private void delLink(Node<T> node) {
        if (size == 1) {
            first = null;
            last = null;
            size--;
        } else if (node == first) {
            first = first.next;
            first.prev = null;
            size--;
        } else if (node == last) {
            last = last.prev;
            last.next = null;
            size--;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
    }

    private Node<T> nodeByIndex(int index) {
        checkPositionIndex(index);
        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }
}
