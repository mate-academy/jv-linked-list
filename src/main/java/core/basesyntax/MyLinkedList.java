package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;

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

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value,null);
        if (head == null && tail == null){
            head = newNode;
            tail = newNode;
        } else {
            Node<T> a = tail;
            a.next = newNode;
            newNode.prev = a;
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
                    Node<T> a = current.prev;
                    a.next = newNode;
                    newNode.prev = a;
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
            if (head == null && tail == null){
                head = newNode;
                tail = newNode;
            } else {
                Node<T> a = tail;
                a.next = newNode;
                newNode.prev = a;
                tail = newNode;
            }
        }
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int count = 0;
        Node<T> current = head;
        while(current != null) {
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
        T a = null;
        while(current != null) {
            if (count == index) {
                a = current.item;
                current.item = value;
                break;
            }
            current = current.next;
            count++;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        return a;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        int count = 0;
        T result = null;
        while(current != null) {
            if (count == index) {
                result = current.item;
                Node<T> a = current.prev;
                Node<T> b = current.next;
                if (size() == 1) {
                    head = null;
                    tail = null;
                    break;
                }
                if (a == null) {
                    b.prev = null;
                    head = b;
                    break;
                }
                if (b == null){
                    a.next = null;
                    tail = a;
                    break;
                }
                a.next = b;
                b.prev = a;
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
            if (Objects.equals(current.item, object) && size() == 1) {
                head = null;
                tail = null;
                return true;
            }
            if (Objects.equals(current.item, object) && current.next == null) {
                Node<T> a = current.prev;
                a.next = null;
                tail = a;
                return true;
            }
            if (Objects.equals(current.item, object) && head == current) {
                Node<T> a = current.next;
                a.prev = null;
                head = a;
                return true;
            }
            if (Objects.equals(current.item, object)) {
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
        while(a != null) {
            count++;
            a = a.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        if (head == null && tail == null) {
            return true;
        } else {
            return false;
        }
    }
}
