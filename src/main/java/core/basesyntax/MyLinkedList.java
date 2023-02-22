package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> newNode = new Node<>(value, tail, null);
        if (tail != null) {
            tail.setNext(newNode);
        }
        checkHeadTail(newNode);
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> replacedNode;
        Node<T> newNode;
        if (index == size) {
            newNode = new Node<>(value, tail, null);
        } else {
            replacedNode = getAt(index);
            if (replacedNode != null) {
                newNode = new Node<>(value, replacedNode.getPrevious(), replacedNode);
                replacedNode.setPrevious(newNode);
            } else {
                newNode = new Node<>(value, null, null);
            }
        }
        if (newNode.getPrevious() != null) {
            newNode.getPrevious().setNext(newNode);
        }
        checkHeadTail(newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T toAdd : list) {
            add(toAdd);
        }
    }

    @Override
    public T get(int index) {
        return getAt(index).value;
    }

    @Override
    public T set(T value, int index) {
        final Node<T> found = getAt(index);
        final T oldValue = found.getValue();
        found.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> found = getAt(index);
        unlinc(found);
        return found.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            T value = current.getValue();
            if (value == null && object == null
                    || value != null && value.equals(object)) {
                unlinc(current);
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    private void unlinc(Node<T> found) {
        if (found.getNext() == null && found.getPrevious() == null) {
            setHead(null);
            setTail(null);
        } else if (found.getPrevious() == null) {
            head = found.getNext();
            head.setPrevious(null);
        } else if (found.getNext() == null) {
            tail = found.getPrevious();
            tail.setNext(null);
        } else {
            found.getPrevious().setNext(found.getNext());
            found.getNext().setPrevious(found.getPrevious());
        }
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getAt(int index) {
        checkBounds(index);
        Node<T> result;
        int middle = (size - 1) / 2;
        if (index < middle) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.getNext();
            }
        } else {
            result = tail;
            for (int i = 0; i < size - (index + 1); i++) {
                result = result.getPrevious();
            }
        }
        return result;
    }

    private void checkBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of range 0 - %d", index, size)
            );
        }
    }

    private void checkHeadTail(Node<T> node) {
        if (node.getNext() == null) {
            setTail(node);
        }
        if (node.getPrevious() == null) {
            setHead(node);
        }
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    private class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
