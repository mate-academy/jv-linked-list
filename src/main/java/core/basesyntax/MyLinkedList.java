package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null,value,null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.setPrev(current.getPrev());
            newNode.setNext(current);
            current.getPrev().setNext(newNode);
            current.setPrev(newNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove;
        if (index == 0) {
            nodeToRemove = head;
            head = head.getNext();
            if (head != null) {
                head.setPrev(null);
            } else {
                tail = null;
            }
        } else if (index == size - 1) {
            nodeToRemove = tail;
            tail = tail.getPrev();
            if (tail != null) {
                tail.setNext(null);
            } else {
                head = null;
            }
        } else {
            nodeToRemove = getNode(index);
            nodeToRemove.getPrev().setNext(nodeToRemove.getNext());
            nodeToRemove.getNext().setPrev(nodeToRemove.getPrev());
        }
        size--;
        return nodeToRemove.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.getValue(), object)) {
                if (current == head) {
                    head = head.getNext();
                    if (head != null) {
                        head.setPrev(null);
                    } else {
                        tail = null;
                    }
                } else if (current == tail) {
                    tail = tail.getPrev();
                    if (tail != null) {
                        tail.setNext(null);
                    } else {
                        head = null;
                    }
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
