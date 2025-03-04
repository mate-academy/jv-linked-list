package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private MyLinkedList<T> next;
    private MyLinkedList<T> prev;
    private MyLinkedList<T> firstNode;
    private T value;
    private int size;

    public MyLinkedList() {
        this.next = null;
        this.prev = null;
        this.firstNode = null;
        size = 0;
        this.value = null;
    }

    public MyLinkedList(T value) {
        this.value = value;
    }

    @Override
    public void add(T value) {
        MyLinkedList newNode = new MyLinkedList(value);
        if (firstNode == null) {
            firstNode = newNode;
        } else {
            MyLinkedList indexNode = firstNode;
            while (indexNode.next != null) {
                indexNode = indexNode.next;
            }
            newNode.prev = indexNode;
            indexNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index);
        }

        MyLinkedList<T> nodeValue = new MyLinkedList<>(value);

        if (size == 0 && index == 0) {
            firstNode = nodeValue;
        } else if (index == 0) {
            nodeValue.next = firstNode;
            firstNode.prev = nodeValue;
            firstNode = nodeValue;
        } else {
            MyLinkedList current = firstNode;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            if (current.next == null) {
                current.next = nodeValue;
                nodeValue.prev = current;
            } else {
                nodeValue.next = current.next;
                nodeValue.prev = current;
                current.next.prev = nodeValue;
                current.next = nodeValue;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index is not allowed");
        }

        if (index < size) {
            MyLinkedList<T> current = firstNode;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.value;
        }
        throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds");
    }

    @Override
    public T set(T value, int index) {
        getException(index);
        MyLinkedList<T> current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        MyLinkedList<T> newValue = new MyLinkedList<>(value);
        final T oldValue = current.value;

        if (index == 0) {
            newValue.next = current.next;
            if (current.next != null) {
                current.next.prev = newValue;
            }
            firstNode = newValue;
        } else if (current.next == null) {
            newValue.prev = current.prev;
            current.prev.next = newValue;
        } else {
            newValue.prev = current.prev;
            newValue.next = current.next;
            current.prev.next = newValue;
            current.next.prev = newValue;
        }
        current.next = null;
        current.prev = null;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        getException(index);

        MyLinkedList<T> current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        final T removeValue = current.value;

        if (index == 0) {
            firstNode = current.next;
            if (current.next != null) {
                firstNode.prev = null;
            }
            current.next = null;
        } else if (current.next == null) {
            if (current.prev != null) {
                current.prev.next = current.next;
            }
            current.prev = null;
        } else {
            current.next.prev = current.prev;
            current.prev.next = current.next;
            current.next = null;
            current.prev = null;
        }
        size--;
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        if (firstNode == null) {
            return false;
        }

        MyLinkedList<T> current = firstNode;
        while (current != null && !(current.value == object
            || (current.value != null && current.value.equals(object)))) {
            current = current.next;
        }
        if (current == null) {
            return false;
        }

        if (current.prev == null) {
            firstNode = current.next;
            if (firstNode != null) {
                firstNode.prev = null;
            }
        } else if (current.next == null) {
            current.prev.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        current.next = null;
        current.prev = null;
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

    private void getException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
    }

}
