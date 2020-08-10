package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    transient int size;
    transient Node<T> first;
    transient Node<T> last;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> before = last;
        if (size == 1) {
            before = first;
        }
        Node<T> node = new Node(before, value, null);
        if (size == 0) {
            first = node;
        }
        if (size >= 1) {
            before.next = node;
        }
        last = node;
        size++;

        return true;
    }

    @Override
    public void add(T value, int index) {
        isIndexGood(index);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> node = new Node<>(null, value, first);
            first.prev = node;
            first = node;
            size++;
            return;
        } else {
            Node<T> node = new Node<>(findNodeWithIndex(index).prev,
                    value, findNodeWithIndex(index));
            findNodeWithIndex(index).prev.next = node;
            findNodeWithIndex(index).prev = node;
            size++;
            return;
        }
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
        isIndexGood(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        return findNodeWithIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        isIndexGood(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Wring index");
        }
        T valueBeforeSetting = findNodeWithIndex(index).item;
        findNodeWithIndex(index).item = value;
        return valueBeforeSetting;
    }

    @Override
    public T remove(int index) {
        isIndexGood(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        Node<T> node = findNodeWithIndex(index);
        T item = node.item;
        if (index == size - 1 && index == 0) {
            first.item = null;
            last.item = null;
            size--;
        } else if (index == 0 && index != size - 1) {
            node.next.prev = null;
            first = node.next;
            node.next = null;
            node.item = null;
            size--;
        } else if (index == size - 1 && index != 0) {
            node.prev.next = null;
            last = node.prev;
            node.prev = null;
            node.item = null;
            size--;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            unlinkNode(node);
            size--;
        }
        return item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == t
                    || (node.item != null && node.item.equals(t))) {
                remove(i);
                return true;
            }
            node = node.next;
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

    public boolean isIndexGood(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        } else {
            return true;
        }
    }

    public Node<T> findNodeWithIndex(int index) {
        Node<T> nodeWithIndex = first;
        for (int i = 0; i < index; i++) {
            nodeWithIndex = nodeWithIndex.next;
        }
        return nodeWithIndex;
    }

    public Node<T> unlinkNode(Node<T> node) {

        node.prev = null;
        node.next = null;
        node.item = null;
        return node;
    }
}

