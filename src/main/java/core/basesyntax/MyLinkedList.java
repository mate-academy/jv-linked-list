package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        setHead(null);
        setTail(null);
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (getHead() == null) {
            listStart(node);
        } else {
            addToEnd(node);
        }
        setSize(getSize() + 1);
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = new Node<>(null, value, null);
        checkIndexAdd(index);
        if (getHead() == null) {
            listStart(node);
        } else if (index == 0) {
            node.setNext(getHead());
            getHead().setPrev(node);
            setHead(node);
        } else if (index == getSize()) {
            addToEnd(node);
        } else {
            Node<T> current = nodeIterator(index);
            node.setPrev(current.getPrev());
            node.setNext(current);
            current.getPrev().setNext(node);
            current.setPrev(node);
        }
        setSize(getSize() + 1);
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
        return nodeIterator(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> targetNode = nodeIterator(index);
        T oldValue = targetNode.getValue();
        targetNode.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = nodeIterator(index);
        if (getSize() == 1) {
            setHead(null);
            setTail(null);
        }
        unlink(current);
        setSize(getSize() - 1);
        return current.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = nodeIterator(object);
        if (current == null) {
            return false;
        }
        if (getSize() == 1) {
            setHead(null);
            setTail(null);
        }
        unlink(current);
        setSize(getSize() - 1);
        return true;
    }

    @Override
    public int size() {
        return getSize();
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    private void listStart(Node<T> node) {
        setHead(node);
        setTail(node);
    }

    private void addToEnd(Node<T> node) {
        getTail().setNext(node);
        node.setPrev(getTail());
        setTail(node);
    }

    private void checkIndexAdd(int index) {
        if (index > getSize() || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void checkIndex(int index) {
        if (index > getSize() - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> nodeIterator(int index) {
        Node<T> current;
        if (index < getSize() / 2) {
            current = getHead();
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = getTail();
            for (int i = getSize() - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    private Node<T> nodeIterator(T value) {
        Node<T> current = getHead();
        while (current != null) {
            if (current.getValue() == null && value == null
                    || current.getValue() != null
                    && current.getValue().equals(value)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    private void unlink(Node<T> current) {
        if (current.getPrev() != null) {
            current.getPrev().setNext(current.getNext());
        } else {
            setHead(current.getNext());
        }
        if (current.getNext() != null) {
            current.getNext().setPrev(current.getPrev());
        } else {
            setTail(current.getPrev());
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
