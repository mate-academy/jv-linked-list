package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(value);
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
       throw new IndexOutOfBoundsException();
                                          }
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            size++;
            if (tail == null) {
                tail = head;
            }
        } else if (index == size) {
            add(value);
            return;

        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;

            }
            //  1 -> 4 -> 2 -> 3
            //curent.prev  <-  newNode -> current

            Node<T> temp = current;
            current.prev = new Node<>(value);
            current.prev.next = temp;
            size++;
        }

    }


    @Override
    public void addAll(List<T> list) {
        for (T l : list) {
            list.add((T) l);
        }

    }

    @Override
    public T get(int index) {
       checkIndex(index);
        Node<T> temp = head; 
        if (index < size && index > 0) {
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        }
        return temp.value;
    }


    @Override
    public T set(T value, int index) {
        Node<T> current = head;
        checkIndex(index);
        if (index < size && index >= 0) {
            for (int i = 0; i <= index; i++) {
                current = current.next;
            }
        }
        return current.value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
         if (index == 0) {
            //if (size == 0) {
            // return null;
            //} else {
            T temp = head.value;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return temp;
        } else if (index == size - 1) {
            if (size == 0) {
                return null;
            } else if (size == 1) {
                T temp = head.value;
                head = tail = null;
                size = 0;
                return temp;
            } else {
                Node<T> current = head;
                for (int i = 0; i < size - 2; i++) {
                    current = current.next;
                }
                T temp = tail.value;
                tail = current;
                tail.next = null;
                size--;
                return temp;
            }
        } else {
            Node<T> previous = head;
            for (int i = 0; i < index; i++) {
                previous = previous.next;
            }
            Node<T> current = previous.next;
            previous.next = current.next;
            size--;
            return current.next.value;
        }
    }

    @Override
    public boolean remove(T object) {
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

    static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }

    public void unlink(Node<T> current) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (current.prev == null) {
            head = current.next;
            head.prev = null;
        } else if (current.next == null) {
            tail = current.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}