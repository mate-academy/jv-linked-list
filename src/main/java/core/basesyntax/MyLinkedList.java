package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            first = last = newNode;
            size++;
        } else if (size == 1) {
            newNode.prev = first;
            first.next = newNode;
            last = newNode;
            size++;
        } else {
            Node<T> oldLast = last;
            newNode.prev = oldLast;
            oldLast.next = newNode;
            last = newNode;
            size++;
        }
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
        if (index >= 0 && index < size) {
            if (size == 1 && index == 0) {
                return first.value;
            } else {
                Node<T> current = first;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
                return current.value;
            }
        } else {
            throw new IndexOutOfBoundsException("Index is out of Bounds");
        }
    }

    @Override
    public T set(T value, int index) {
        if (index >= 0 && index < size) {
            Node<T> target = getNode(index);
            T oldValue = target.value;
            target.value = value;
            return oldValue;
        } else {
            throw new IndexOutOfBoundsException("Index is out of Bounds");
        }
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            Node<T> target = getNode(index);
            T current = target.value;
            remove(target.value);
            return current;
        } else {
            throw new IndexOutOfBoundsException("Index is out of Bounds");
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.value == object || object != null && object.equals(current.value)) {
                if (size == 1) {
                    first = null;
                    size--;
                    return true;
                } else if (size > 1 && i == 0) {
                    //T value = first.value;
                    Node<T> newNode = first.next;
                    newNode.prev = null;
                    first = newNode;
                    size--;
                    return true;
                } else {
                    Node<T> prevCurrentNode = current.prev;
                    Node<T> nextCurrentNode = current.next;
                    prevCurrentNode.next = nextCurrentNode;
                    nextCurrentNode.prev = prevCurrentNode;
                    size--;
                    return true;
                }
            }
            current = current.next;
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
        Node<T> target = first;
        if (index >= 0 && index < size) {
            for (int i = 0; i < index; i++) {
                target = target.next;
            }
            return target;
        } else {
            throw new IndexOutOfBoundsException("Index is out of Bounds");
        }
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
}
