package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    transient int size = 0;
    transient Node<T> head;
    transient Node<T> tail;

    void linkFirst(T value) {
        Node<T> temp = head;
        Node<T> newNode = new Node<>(value, null, temp);
        head = newNode;
        if (temp == null) {
            tail = newNode;
        } else {
            temp.prev = newNode;
        }
        size++;
    }

    public boolean isIndexOf(int index) {
        return index >= 0 && index < size;
    }

    void indexChecker(int index) {
        if (!isIndexOf(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    void linkLast(T value) {
        Node<T> temp = tail;
        Node<T> newNode = new Node<>(value, temp, null);
        tail = newNode;
        if (temp == null) {
            head = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
    }

    void linkBefore(T value, Node<T> node) {
        Node<T> priv = node.prev;
        Node<T> newNode = new Node<>(value, priv, node);
        node.prev = newNode;
        if (priv == null) {
            head = newNode;
        } else {
            priv.next = newNode;
        }
        size++;
    }

    public T unlinkFirst() {
        T temp = head.value;
        Node<T> next = head.next;
        head.next = null;
        head.value = null;
        head = next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        return temp;
    }

    public T unlinkLast() {
        T temp = tail.value;
        Node<T> prev = tail.prev;
        tail.prev = null;
        tail.value = null;
        tail = prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return temp;
    }

    public Node<T> find(T value) {
        if (value == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    return x;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (value.equals(x.value)) {
                    return x;
                }
            }
        }
        return null;
    }

    public T unlink(Node<T> node) {
        T value = node.value;
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        node.next = null;
        node.prev = null;
        node.value = null;
        size--;
        return value;
    }

    public Node<T> findByIndex(int index) {
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            indexChecker(index);
            linkBefore(value, findByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        indexChecker(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexChecker(index);
        Node<T> temp = findByIndex(index);
        T oldValue = temp.value;
        temp.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexChecker(index);
        final Node<T> temp = findByIndex(index);
        T oldValue = temp.value;
        if (temp.prev == null) {
            return unlinkFirst();
        } else if (temp.next == null) {
            return unlinkLast();
        } else {
            unlink(temp);
        }
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = find(object);
        if (node == null) {
            return false;
        }

        if (node.next == null) {
            unlinkLast();
        } else if (node.prev == null) {
            unlinkFirst();
        } else {
            unlink(node);
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
