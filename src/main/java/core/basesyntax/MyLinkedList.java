package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value,null);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            Node<T> previousNode = tail;
            previousNode.next = newNode;
            newNode.prev = previousNode;
            tail = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index > size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node<>(null, value,null);
        int count = 0;
        boolean inserted = false;
        Node<T> current = head;
        while (current != null) {
            if (count == index) {
                if (current == head) {
                    newNode.next = head;
                    head.prev = newNode;
                    head = newNode;
                } else {
                    Node<T> previousNode = current.prev;
                    previousNode.next = newNode;
                    newNode.prev = previousNode;
                    current.prev = newNode;
                    newNode.next = current;
                }
                inserted = true;
                break;
            }
            current = current.next;
            count++;
        }
        if (!inserted) {
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            Node<T> newNode = new Node<>(null, value,null);
            if (head == null && tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                Node<T> previousNode = tail;
                previousNode.next = newNode;
                newNode.prev = previousNode;
                tail = newNode;
            }
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            if (count == index) {
                return current.item;
            }
            current = current.next;
            count++;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        int count = 0;
        T currentIndex = null;
        while (current != null) {
            if (count == index) {
                currentIndex = current.item;
                current.item = value;
                break;
            }
            current = current.next;
            count++;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        return currentIndex;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        int count = 0;
        T result = null;
        while (current != null) {
            if (count == index) {
                result = current.item;
                Node<T> prevNode = current.prev;
                Node<T> nextNode = current.next;
                if (size() == 1) {
                    head = null;
                    tail = null;
                    break;
                }
                if (prevNode == null) {
                    nextNode.prev = null;
                    head = nextNode;
                    break;
                }
                if (nextNode == null) {
                    prevNode.next = null;
                    tail = prevNode;
                    break;
                }
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                break;
            }
            current = current.next;
            count++;
        }
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (equals(current.item, object) && size() == 1) {
                head = null;
                tail = null;
                return true;
            }
            if (equals(current.item, object) && current.next == null) {
                Node<T> a = current.prev;
                a.next = null;
                tail = a;
                return true;
            }
            if (equals(current.item, object) && head == current) {
                Node<T> a = current.next;
                a.prev = null;
                head = a;
                return true;
            }
            if (equals(current.item, object)) {
                Node<T> a = current.prev;
                Node<T> b = current.next;
                a.next = b;
                b.prev = a;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        Node<T> a = head;
        int count = 0;
        if (head == null) {
            return 0;
        }
        while (a != null) {
            count++;
            a = a.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
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
