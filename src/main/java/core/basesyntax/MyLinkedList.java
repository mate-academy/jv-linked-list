package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(last, value, null);
        if (first == null) {
            first = node;
            last = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            Node<T> node = getNodeByIndex(index);
            Node<T> prevNode = node.prev;
            prevNode.next = newNode;
            node.prev = newNode;
            newNode.next = node;
            newNode.prev = prevNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("List is empty!");
        }
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        return newNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNodeByIndex(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNodeByValue(object);
        unlink(node);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> element;
        if (index <= size / 2) {
            int i = 0;
            for (element = first; element != null; element = element.next) {
                if (i == index) {
                    break;
                }
                i++;
            }
        } else {
            int i = size - 1;
            for (element = last; element != null; element = element.prev) {
                if (i == index) {
                    break;
                }
                i--;
            }
        }
        return element;
    }

    private int getIndexByValue(T object) {
        int i = 0;
        for (Node<T> element = first; element != null; element = element.next) {
            if (element.item == object || element.item != null && element.item.equals(object)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private Node<T> getNodeByValue(T object) {
        int i = 0;
        for (Node<T> element = first; element != null; element = element.next) {
            if (element.item == object || element.item != null && element.item.equals(object)) {
                return element;
            }
            i++;
        }
        throw new RuntimeException("Can't find this object " + object + " in the list");
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void unlink(Node<T> node) {
        if (first == node && last == node) {
            first = null;
            last = null;
        } else if (first != null && first == node) {
            Node<T> newFirst = node.next;
            newFirst.prev = null;
            first = newFirst;
        } else if (last != null && last == node) {
            Node<T> newLast = node.prev;
            newLast.next = null;
            last = newLast;
        } else {
            Node<T> prevNode = node.prev;
            Node<T> nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
    }
}
