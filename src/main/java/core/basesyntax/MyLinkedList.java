package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        last = new Node<>(oldLast, value, null);
        if (oldLast == null) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> target = getNode(index);
            linkBefore(value, target);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> target = getNode(index);
        T oldValue = target.value;
        target.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNode(index);
        unlink(target);
        return target.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node != null) {
            return unlink(node);
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> target = null;
        int point = size / 2;
        if (size == 1) {
            return first;
        } else {
            if (index <= point) {
                target = first;
                for (int i = 0; i < index; i++) {
                    target = target.next;
                }
            } else {
                target = last;
                for (int i = size - 1; i > index; i--) {
                    target = target.prev;
                }
            }
            return target;
        }
    }

    private Node<T> getNode(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (object == current.value || object != null && object.equals(current.value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private void linkBefore(T value, Node<T> target) {
        Node<T> newNode;
        if (target == first) {
            newNode = new Node<T>(null, value, target);
            first = newNode;
            target.prev = newNode;
        } else {
            newNode = new Node<T>(target.prev, value, target);
            target.prev.next = newNode;
            target.prev = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is outside the bounds of an array of size " + size);
        }
    }

    private boolean unlink(Node<T> target) {
        if (size == 1) {
            first = last = null;
        } else if (target == first) {
            Node<T> rightNode = target.next;
            rightNode.prev = null;
            first = rightNode;
        } else if (target == last) {
            Node<T> leftNode = target.prev;
            leftNode.next = null;
            last = leftNode;
        } else {
            Node<T> leftNode = target.prev;
            Node<T> rightNode = target.next;
            leftNode.next = rightNode;
            rightNode.prev = leftNode;
        }
        size--;
        return true;
    }
}
