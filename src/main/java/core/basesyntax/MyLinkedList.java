package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            first = node;
            last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Can't add element "
                    + value + " by index " + index);
        }
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(null, value, null);

        if (index == 0) {
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
            size++;
            return;
        }

        Node<T> next = findNodeByIndex(index);
        newNode.prev = next.prev;
        newNode.next = next;
        next.prev.next = newNode;
        next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Can't get element by index " + index);
        }

        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Can't set value by index " + index);
        }

        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Can't remove element by index " + index);
        }

        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;

        while (currentNode != null) {
            if (currentNode.item == null && object == null || currentNode.item != null
                    && currentNode.item.equals(object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> node = first;
        if (size / 2 > index) {
            int i = 0;

            while (i <= index) {
                if (i == index) {
                    break;
                }
                node = node.next;
                i++;
            }
        } else {
            int i = size - 1;
            node = last;

            while (i >= index) {
                if (i == index) {
                    break;
                }
                node = node.prev;
                i--;
            }
        }

        return node;
    }

    private T unlink(Node<T> node) {
        T value = node.item;

        if (size == 1) {
            first = null;
            last = null;
            size = 0;
            return value;
        }

        if (first == node) {
            node.next.prev = null;
            first = node.next;
        } else if (last == node) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;

        return value;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
