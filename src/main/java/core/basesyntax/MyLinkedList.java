package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private final BuilderNode<T> builderNode = new BuilderNode<T>();
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public static class BuilderNode<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public BuilderNode<E> setItem(E item) {
            this.item = item;
            return this;
        }

        public BuilderNode<E> setNext(Node<E> next) {
            this.next = next;
            return this;
        }

        public BuilderNode<E> setPrev(Node<E> prev) {
            this.prev = prev;
            return this;
        }

        public Node<E> build() {
            return new Node<>(prev, item, next);
        }
    }


    @Override
    public void add(T value) {
        Node<T> newNode;
        builderNode.setItem(value);
        if (size == 0) {
            newNode = builderNode
                    .setPrev(tail)
                    .setNext(head)
                    .build();
            head = tail = newNode;
        } else {
            newNode = builderNode
                    .setPrev(tail)
                    .build();
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }


    @Override
    public void add(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index: " + index);
        }
        Node<T> newNode;
        builderNode.setItem(value);
        if (head == null) {
            head = tail = builderNode.build();
        } else if (index == 0) {
            newNode = builderNode
                    .setNext(head)
                    .build();
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode = builderNode
                    .setPrev(tail)
                    .build();
            tail.next = newNode;
            tail = newNode;
        } else {
            checkIndex(index);
            Node<T> prev = getNodeByIndex(index - 1);
            newNode = builderNode
                    .setNext(prev.next)
                    .setPrev(prev)
                    .build();
            prev.next.prev = newNode;
            prev.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedElement;
        if (index == 0) {
            removedElement = head.item;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            Node<T> nodeToRemove = prev.next;
            removedElement = nodeToRemove.item;
            prev.next = nodeToRemove.next;
            if (nodeToRemove == tail) {
                tail = prev;
            } else {
                nodeToRemove.next.prev = prev;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((current.item == null && object == null)
                    || (current.item != null && current.item.equals(object))) {
                if (current == head) {
                    head = head.next;
                    if (head == null) {
                        tail = null;
                    } else {
                        head.prev = null;
                    }
                } else if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                return true;
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
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can`t operate on index: " + index
                    + ", with size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
