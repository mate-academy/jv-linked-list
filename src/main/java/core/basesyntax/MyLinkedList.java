package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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

    @Override
    public void add(T value) {
        Node<T> node;
        if (isEmpty()) {
            node = new Node<>(null, value, null);
            setHead(node);
            setTail(node);
        } else {
            node = new Node<>(tail, value, null);
            tail.setNext(node);
            setTail(node);
        }
        setTail(node);
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of range " + size());
        }
        Node<T> insert;
        if (index == 0 && getHead() != null) {
            insert = new Node<>(null, value, getHead());
            getHead().setPrev(insert);
            setHead(insert);
            size++;
        } else if (getHead() == null || index == size()) {
            add(value);
        } else {
            Node<T> old = getNode(index);
            insert = new Node<>(old.getPrev(), value, old);
            old.getPrev().setNext(insert);
            old.setPrev(insert);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkRange(index);
        return getNode(index).getItem();
    }

    @Override
    public T set(T value, int index) {
        checkRange(index);
        T oldItem = getNode(index).getItem();
        getNode(index).setItem(value);
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        Node<T> temp = getNode(index);
        unlink(temp);
        size--;
        return temp.getItem();
    }

    @Override
    public boolean remove(T object) {
        Node<T> test = head;
        if (size() == 1) {
            if (isEquals(object, head.getItem())) {
                size--;
                unlink(test);
                return true;
            }
            return false;
        }
        while (test.getNext() != null) {
            if (isEquals(object, test.getItem())) {
                unlink(test);
                size--;
                return true;
            }
            test = test.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public void unlink(Node<T> node) {
        if (getHead() == node && getTail() == node) {
            node.setNext(null);
            node.setPrev(null);
        } else if (getHead() == node) {
            node.getNext().setPrev(null);
            setHead(node.getNext());
        } else if (getTail() == node) {
            node.getPrev().setNext(null);
            setTail(node.getPrev());
        } else {
            node.getNext().setPrev(node.getPrev());
            node.getPrev().setNext(node.getNext());
        }
    }

    public boolean isEquals(T first, T second) {
        return first == second || first != null && first.equals(second);
    }

    private void checkRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of range " + (size() - 1));
        }
    }

    private Node<T> getNode(int index) {
        Node<T> element = (index < size() / 2) ? head : tail;
        int counter = (element == head) ? 0 : (size() - 1);
        if (counter < index) {
            while (counter != index) {
                element = element.next;
                counter++;
            }
            return element;
        }
        while (counter != index) {
            element = element.prev;
            counter--;
        }
        return element;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
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
    }
}
