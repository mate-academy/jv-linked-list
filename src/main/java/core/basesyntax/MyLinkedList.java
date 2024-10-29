package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node node = new Node<>(null, value, null);
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node node = new Node<>(null, value, null);

        if (index == 0) {
            if (size == 0) {
                first = last = node;
            } else {
                node.next = first;
                first.prev = node;
                first = node;
            }
        } else if (index == size) {
            last.next = node;
            node.prev = last;
            last = node;
        } else {
            Node n = first;
            for (int i = 0; i < index - 1; i++) {
                n = n.next;
            }
            node.next = n.next;
            node.prev = n;
            n.next.prev = node;
            n.next = node;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Node newNode = new Node<>(last, list.get(i), null);

            if (size == 0) {
                first = newNode;
                last = newNode;
            } else {
                last.next = newNode;
                last = newNode;
            }

            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node n = first;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        return (T) n.item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node n = first;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }

        T oldValue = (T) n.item;
        n.item = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        T delItem;

        if (index == 0) {
            delItem = (T) first.item;
            first = first.next;
            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
        } else if (index == size - 1) {
            delItem = (T) last.item;
            last = last.prev;
            if (last != null) {
                last.next = null;
            } else {
                first = null;
            }
        } else {
            Node n = first;
            for (int i = 0; i < index - 1; i++) {
                n = n.next;
            }
            Node delNode = n.next;
            delItem = (T) delNode.item;

            n.next = delNode.next;
            if (delNode.next != null) {
                delNode.next.prev = n;
            }
        }

        size--;
        return delItem;
    }

    @Override
    public boolean remove(T object) {
        Node node = first;

        while (node != null) {
            if (node.item != null && node.item.equals(object) || node.item == object) {
                if (node.prev != null) {
                    node.prev.next = node.next;
                } else {
                    first = node.next;
                }

                if (node.next != null) {
                    node.next.prev = node.prev;
                } else {
                    last = node.prev;
                }

                size--;
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

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
