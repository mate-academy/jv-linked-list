package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private final CheckIndexForAdd indexForAddChecker = new CheckIndexForAdd();
    private final CheckIndex indexChecker = new CheckIndex();

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexForAddChecker.checkIndexForAdd(index, size);
        Node<T> newNode = new Node<>(value);

        if (index == 0) {
            newNode.setNext(head);

            if (head != null) {
                head.setPrev(newNode);
            }

            head = newNode;

            if (tail == null) {
                tail = head;
            }
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> current = getNode(index);

            Node<T> previous = current.getPrev();

            previous.setNext(newNode);
            newNode.setPrev(previous);

            current.setPrev(newNode);
            newNode.setNext(current);
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
        indexChecker.checkIndex(index, size);
        return getNode(index).getData();
    }

    @Override
    public T set(T value, int index) {
        indexChecker.checkIndex(index, size);

        Node<T> current = getNode(index);
        T oldValue = current.getData();

        current.setData(value);

        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexChecker.checkIndex(index, size);

        Node<T> current = getNode(index);

        if (current == head) {
            head = head.getNext();

            if (head != null) {
                head.setPrev(null);
            }
        } else if (current == tail) {
            tail = tail.getPrev();
            if (tail != null) {
                tail.setNext(null);
            }
        } else {
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
        }
        size--;

        if (size == 0) {
            head = tail = null;
        }
        return current.getData();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            if (object == null
                    ? current.getData() == null
                    : object.equals(current.getData())) {
                remove(index);
                return true;
            }
            current = current.getNext();
            index++;
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
        Node<T> current;
        if (index < size / 2) {
            current = head;

            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;

            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }
}
