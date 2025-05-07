package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            Node<T> currentNode = getNode(index);
            if (currentNode == first) {
                first = currentNode.prev = new Node<>(null, value, currentNode);
                size++;
            } else {
                Node<T> prev = currentNode.prev;
                Node<T> newNode = new Node<>(prev, value, currentNode);
                prev.next = newNode;
                currentNode.prev = newNode;
                size++;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T temp = currentNode.item;
        currentNode.item = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNode(index);
        unlink(target);
        return target.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
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
        return size == 0;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds List");
        }
    }

    private Node<T> getNode(int index) {
        validateIndex(index);
        Node<T> currentNode;
        if (index > size / 2) {
            currentNode = last;
            for (int i = size; i > index + 1; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> target) {
        if (size == 1) {
            target.prev = null;
            target.next = null;
        } else if (target == first) {
            first = target.next;
            target.next = null;
        } else if (target == last) {
            last = target.prev;
            target.prev = null;
        } else {
            Node<T> next = target.next;
            Node<T> prev = target.prev;
            next.prev = prev;
            prev.next = next;
            target.next = target.prev = null;
        }
        size--;
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
}
