package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> head;
    Node<T> tail;
    int size;

    public void listStart(Node<T> node) {
        head = node;
        tail = node;
    }

    public void addToEnd(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    public MyLinkedList() {
        head = null;
        tail = null;
    }

    public void checkIndexAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    public void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    public Node<T> nodeIterator(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public Node<T> nodeIterator(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == null && value == null
                    || current.value != null
                    && current.value.equals(value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void removeFirst(Node<T> current) {
        head = current.next;
        if (head != null) {
            head.prev = null;
        }
    }

    public void removeLast(Node<T> current) {
        tail = current.prev;
        if (tail != null) {
            tail.next = null;
        }
    }

    public void removeMiddle(Node<T> current) {
        current.prev.next = current.next;
        current.next.prev = current.prev;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            listStart(node);
        } else {
            addToEnd(node);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = new Node<>(null, value, null);
        checkIndexAdd(index);
        if (head == null) {
            listStart(node);
        } else if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
        } else if (index == size) {
            addToEnd(node);
        } else {
            Node<T> current = nodeIterator(index);
            node.prev = current.prev;
            node.next = current;
            current.prev.next = node;
            current.prev = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodeIterator(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> targetNode = nodeIterator(index);
        T oldValue = targetNode.value;
        targetNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = nodeIterator(index);
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            removeFirst(current);
        } else if (index == size - 1) {
            removeLast(current);
        } else {
            removeMiddle(current);
        }
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = nodeIterator(object);
        if (current == null) {
            return false;
        }
        if (size == 1) {
            head = null;
            tail = null;
        } else if (current == head) {
            removeFirst(current);
        } else if (current == tail) {
            removeLast(current);
        } else {
            removeMiddle(current);
        }
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
