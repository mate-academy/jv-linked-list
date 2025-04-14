package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> tail;
    private Node<T> head;
    private int size;

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
        if (index > size) {

            throw new IndexOutOfBoundsException("Index is invalid");
        }

        Node<T> searched = node(index);
        if (index != size) {
            Node<T> before = searched.prev;
            Node<T> insertNode = new Node<>(before, value, searched);
            searched.prev = insertNode;
            if (before == null) {
                head = insertNode;
            } else {
                before.next = insertNode.next;
                insertNode.prev = before;
                before.next = insertNode;
            }
        } else {
            searched = tail;
            Node<T> insertNode = new Node<>(searched, value, null);
            tail = insertNode;
            tail.prev = insertNode.prev;
            tail.next = null;
            if (searched == null) {
                head = insertNode;
            } else {
                searched.next = insertNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        T[] listToArray = (T[]) list.toArray();
        Node<T> previous;
        previous = tail;
        for (T current : listToArray) {
            Node<T> newNode = new Node<>(previous, current, null);
            if (previous == null) {
                head = newNode;
            } else {
                previous.next = newNode;
                previous = newNode;
            }
        }
        tail = previous;
        size += list.size();
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
        Node<T> searched;
        searched = node(index);
        return searched.value;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }

        Node<T> searched = node(index);
        T prevValue = searched.value;
        searched.value = value;

        return prevValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
        Node<T> searched = node(index);
        unlink(searched);
        size--;
        return searched.value;
    }

    @Override
    public boolean remove(T object) {
        boolean wasRemove = false;
        Node<T> current;
        if (object == null) {
            for (current = head; current != null; current = current.next) {
                if (current.value == null) {
                    unlink((current));
                    size--;
                    return true;
                }
            }
        } else {
            for (current = head; current != null; current = current.next) {
                if (object.equals(current.value)) {
                    unlink(current);
                    wasRemove = true;
                    size--;
                    break;
                }
            }
        }
        return wasRemove;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;

        }
    }

    private Node<T> node(int index) {
        Node<T> searchedByIndexNode;
        if (index < (size >> 1)) {
            searchedByIndexNode = head;
            for (int i = 0; i < index; i++) {
                searchedByIndexNode = searchedByIndexNode.next;
            }
        } else {
            searchedByIndexNode = tail;
            for (int i = size - 1; i > index; i--) {
                searchedByIndexNode = searchedByIndexNode.prev;
            }
        }
        return searchedByIndexNode;
    }
}
