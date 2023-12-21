package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static int COUNT = 2;
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == size) {
            addLast(value);
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(value, current.getPrev(), current);
            if (current.getPrev() != null) {
                current.getPrev().setNext(newNode);
            } else {
                first = newNode;
            }
            current.setPrev(newNode);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addLast(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return getNode(index).getData();
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T oldValue = current.getData();
        current.setData(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        if (current.getPrev() == null) {
            first = current.getNext();
        } else {
            current.getPrev().setNext(current.getNext());
        }
        if (current.getNext() != null) {
            current.getNext().setPrev(current.getPrev());
        } else {
            last = current.getPrev();
        }
        size--;
        return current.getData();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if ((current.getData() == null && object == null)
                    || (current.getData() != null && current.getData().equals(object))) {
                if (current.getPrev() != null) {
                    current.getPrev().setNext(current.getNext());
                } else {
                    first = current.getNext();
                }
                if (current.getNext() != null) {
                    current.getNext().setPrev(current.getPrev());
                } else {
                    last = current.getPrev();
                }
                size--;
                return true;
            }
            current = current.getNext();
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

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current;
        if (index < size / COUNT) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }

        return current;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (last != null) {
            last.setNext(newNode);
        } else {
            first = newNode;
        }
        last = newNode;
        size++;
    }
}
