package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> nodeByIndex = first;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
            Node<T> nodeValue = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = nodeValue;
            nodeByIndex.prev = nodeValue;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> nodeByIndex = first;
        for (int i = 0; i < index; i++) {
            nodeByIndex = nodeByIndex.next;
        }
        return (T) nodeByIndex.item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> nodeByIndex = first;
        for (int i = 0; i < index; i++) {
            nodeByIndex = nodeByIndex.next;
        }
        T oldValue = nodeByIndex.item;
        nodeByIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> deletedNode;
        if (size == 1) {
            deletedNode = first;
            first = null;
            last = null;
        } else if (index == 0) {
            deletedNode = first;
            first = first.next;
            first.prev = null;
        } else if (index == size - 1) {
            deletedNode = last;
            last = last.prev;
            last.next = null;
        } else {
            Node<T> nodeByIndex = first;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
            deletedNode = nodeByIndex;
            nodeByIndex.prev.next = nodeByIndex.next;
            nodeByIndex.next.prev = nodeByIndex.prev;
        }
        size--;
        return deletedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeForLoop = first;
        int index = 0;
        boolean indicator = false;
        for (int i = 0; i < size; i++) {
            if (object == null && nodeForLoop.item == null) {
                indicator = true;
                break;
            } else if (object == null || nodeForLoop.item == null) {
                nodeForLoop = nodeForLoop.next;
                index++;
                continue;
            }
            if (nodeForLoop.item.equals(object)) {
                indicator = true;
                break;
            }
            nodeForLoop = nodeForLoop.next;
            index++;
        }
        if (indicator) {
            if (nodeForLoop.next == null && nodeForLoop.prev == null) {
                first = null;
                last = null;
                size--;
            } else {
                remove(index);
            }
            return indicator;
        } else {
            return indicator;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
