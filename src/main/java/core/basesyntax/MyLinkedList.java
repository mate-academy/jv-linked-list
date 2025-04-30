package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node firstNode;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node newNode = new Node<>(value);
        if (firstNode == null) {
            firstNode = newNode;
        } else {
            Node indexNode = firstNode;
            while (indexNode.getNext() != null) {
                indexNode = indexNode.getNext();
            }
            newNode.setPrev(indexNode);
            indexNode.setNext(newNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index);
        }

        Node<T> nodeValue = new Node<>(value);

        if (size == 0 && index == 0) {
            firstNode = nodeValue;
        } else if (index == 0) {
            nodeValue.setNext(firstNode);
            firstNode.setPrev(nodeValue);
            firstNode = nodeValue;
        } else {
            Node current = firstNode;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            if (current.getNext() == null) {
                current.setNext(nodeValue);
                nodeValue.setPrev(current);
            } else {
                nodeValue.setNext(current.getNext());
                nodeValue.setPrev(current);
                current.getNext().setPrev(nodeValue);
                current.setNext(nodeValue);
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
            Node<T> current = firstNode;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getValue();
        }
        throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds");
    }

    @Override
    public T set(T value, int index) {
        getException(index);
        Node<T> current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        Node<T> newValue = new Node<>(value);
        final T oldValue = current.getValue();

        if (index == 0) {
            newValue.setNext(current.getNext());
            if (current.getNext() != null) {
                current.getNext().setPrev(newValue);
            }
            firstNode = newValue;
        } else if (current.getNext() == null) {
            newValue.setPrev(current.getPrev());
            current.getPrev().setNext(newValue);
        } else {
            newValue.setPrev(current.getPrev());
            newValue.setNext(current.getNext());
            current.getPrev().setNext(newValue);
            current.getNext().setPrev(newValue);
        }
        current.setNext(null);
        current.setPrev(null);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        getException(index);

        Node<T> current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        final T removeValue = current.getValue();

        if (index == 0) {
            firstNode = current.getNext();
            if (current.getNext() != null) {
                firstNode.setPrev(null);
            }
            current.setNext(null);
        } else if (current.getNext() == null) {
            if (current.getPrev() != null) {
                current.getPrev().setNext(current.getNext());
            }
            current.setPrev(null);
        } else {
            current.getNext().setPrev(current.getPrev());
            current.getPrev().setNext(current.getNext());
            current.setNext(null);
            current.setPrev(null);
        }
        size--;
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        if (firstNode == null) {
            return false;
        }

        Node<T> current = firstNode;
        while (current != null && !(current.getValue() == object
            || (current.getValue() != null && current.getValue().equals(object)))) {
            current = current.getNext();
        }
        if (current == null) {
            return false;
        }

        if (current.getPrev() == null) {
            firstNode = current.getNext();
            if (firstNode != null) {
                firstNode.setPrev(null);
            }
        } else if (current.getNext() == null) {
            current.getPrev().setNext(null);
        } else {
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
        }
        current.setNext(null);
        current.setPrev(null);
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
