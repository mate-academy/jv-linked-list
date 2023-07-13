package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;

    MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
        } else {
            Node node = new Node<>(last, value, null);
            last.setNext(node);
            last = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        validateAddIndex(index);
        Node node = first;
        Node newNode = new Node<>(value);
        if (node == null) {
            first = newNode;
            last = newNode;
            size++;
            return;
        }
        for (int i = 0; i <= size; i++) {
            if (index == i) {
                if (node == null) {
                    last.setNext(newNode);
                    newNode.setPrev(last);
                    last = newNode;
                } else if (i == 0) {
                    node.setPrev(newNode);
                    newNode.setNext(node);
                    first = newNode;
                } else {
                    newNode.setPrev(node.getPrev());
                    node.getPrev().setNext(newNode);
                    node.setPrev(newNode);
                    newNode.setNext(node);
                }
                size++;
                return;
            }
            node = node.getNext();
        }
    }

    @Override
    public void addAll(List<T> list) {
        Node node = last;
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return (T) node.getItem();
            }
            node = node.getNext();
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        final T replacedElement;
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                replacedElement = (T) node.getItem();
                node.setItem(value);
                return replacedElement;
            }
            node = node.getNext();
        }
        return null;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        final T removedElement;
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                if (i == 0 && size == 1) {
                    removedElement = (T) node.getItem();
                    first = null;
                } else if (i == 0) {
                    removedElement = (T) node.getItem();
                    node.getNext().setPrev(node.getPrev());
                    first = node.getNext();
                } else if (i == size - 1) {
                    removedElement = (T) node.getItem();
                    node.getPrev().setNext(node.getNext());
                    last = node.getPrev();
                } else {
                    removedElement = (T) node.getItem();
                    node.getPrev().setNext(node.getNext());
                    node.getNext().setPrev(node.getPrev());
                }
                size--;
                return removedElement;
            }
            node = node.getNext();
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                if (i == 0 && size == 1) {
                    first = null;
                } else if (i == 0) {
                    node.getNext().setPrev(node.getPrev());
                    first = node.getNext();
                } else if (i == size - 1) {
                    node.getPrev().setNext(node.getNext());
                    last = node.getPrev();
                } else {
                    node.getPrev().setNext(node.getNext());
                    node.getNext().setPrev(node.getPrev());
                }
                size--;
                return true;
            }
            node = node.getNext();
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

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index provided");
        }
    }

    private void validateAddIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index provided");
        }
    }

    private static class Node<E> {
        private E item;
        private MyLinkedList.Node<E> next;
        private MyLinkedList.Node<E> prev;

        Node(E element) {
            this.item = element;
            this.next = null;
            this.prev = null;
        }

        Node(MyLinkedList.Node<E> prev, E element, MyLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }
}
