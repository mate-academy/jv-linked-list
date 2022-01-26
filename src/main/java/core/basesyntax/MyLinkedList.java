package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    int size = 0;
    Node<T> first;
    Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkElementIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            Node<T> temp = findByIndex(index);
            Node<T> newNode = new Node<>(temp.prev, value, temp);
            findByIndex(index).prev = newNode;
            if (temp == null) {
                first = newNode;
            } else {
                temp.prev.next = newNode;
            }
            size++;
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
        checkPositionIndex(index);
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> lookedNode = findByIndex(index);
        T oldValue = lookedNode.item;
        lookedNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> temp = findByIndex(index);
        return unlink(temp);
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> lookedNode = first; lookedNode != null; lookedNode = lookedNode.next) {
                if (lookedNode.item == object) {
                    unlink(lookedNode);
                    return true;
                }
            }
        } else {
            for (Node<T> LookedNode = first; LookedNode != null; LookedNode = LookedNode.next) {
                if (object.equals(LookedNode.item)) {
                    unlink(LookedNode);
                    return true;
                }
            }
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

    private void checkPositionIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of the bound");
        }
    }

    private void checkElementIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of bound");
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> lookedNode = first;
        for (int i = 0; i < index; i++) {
            lookedNode = lookedNode.next;
        }
        return lookedNode;
    }

    private void linkLast(T value) {
        Node<T> temp = last;
        Node<T> newNode = new Node<>(last, value, null);
        last = newNode;
        if (temp == null) {
            first = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> temp) {
        if (temp.prev == null) {
            first = temp.next;
        } else if (temp.next == null) {
            last = temp.prev;
        } else {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }
        T oldValue = temp.item;
        temp.item = null;
        size--;
        return oldValue;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
