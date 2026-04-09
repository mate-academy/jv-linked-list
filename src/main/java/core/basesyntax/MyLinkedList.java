package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public Node<T> getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    public Node<T> getTail() {
        return tail;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = node;
            tail = node;
            size++;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
                head = newNode;
            } else {
                head = newNode;
                tail = newNode;
            }
            size++;
            return;
        }
        Node<T> current = head;
        while (current != null && index > 0) {
            current = current.next;
            index--;
        }
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return head.getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head.getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head.getNode(index);

        Node<T> next = current.next;
        Node<T> prev = current.prev;

        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }

        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }

        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        if (current == null) {
            return false;
        }
        if (object == null) {
            if (current.value == null) {
                var next = current.next;
                var prev = current.prev;
                if (next != null) {
                    next.prev = prev;
                } else {
                    tail = prev;
                }

                if (prev != null) {
                    prev.next = next;
                } else {
                    head = next;
                }

                current.next = null;
                current.prev = null;
                size--;
                return true;
            }
            while (current != null) {
                if (current.value == null) {
                    var next = current.next;
                    var prev = current.prev;
                    if (next != null) {
                        next.prev = prev;
                    } else {
                        tail = prev;
                    }

                    if (prev != null) {
                        prev.next = next;
                    } else {
                        head = next;
                    }

                    current.next = null;
                    current.prev = null;
                    size--;
                    return true;
                }
                current = current.next;
            }
        } else {
            while (current != null) {
                if (object.equals(current.value)) {
                    var next = current.next;
                    var prev = current.prev;
                    if (next != null) {
                        next.prev = prev;
                    } else {
                        tail = prev;
                    }

                    if (prev != null) {
                        prev.next = next;
                    } else {
                        head = next;
                    }

                    current.next = null;
                    current.prev = null;
                    size--;
                    return true;
                }
                current = current.next;
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
        return head == null;
    }

    class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        Node<T> getNode(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException();
            }
            Node<T> current = (Node<T>) head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }
}
