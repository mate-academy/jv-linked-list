package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(null,value,null);
            head = node;
            tail = node;
            size++;
        } else {
            Node<T> node = new Node<>(tail,value,null);
            tail.next = node;
            tail = node;
            size++;
        }

    }

    @Override
    public void add(T value, int index) {
        Node<T> node;

        if (index == 0) {
            node = new Node<>(null,value,head);

            if (head != null) {
                node.next = head;
                head.prev = node;
            } else {
                head = node;
                tail = node;
            }
            head = node;
            size++;
        } else {
            if (index == size) {
                node = new Node<>(tail,value,null);
                node.prev.next = node;
                tail = node;
                size++;
            } else if (index > size || index < 0) {
                throw new IndexOutOfBoundsException();
            } else {
                node = head;
                for (int i = 0; i < size; i++) {
                    if (i == index) {
                        node = new Node<>(node.prev,value,node);
                        node.prev.next = node;
                        break;
                    }
                    node = node.next;
                }
                size++;
            }

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
        Node<T> node = head;
        if (index > 0 || index > (size - 1)) {
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    return node.value;
                } else {
                    if (node.equals(tail)) {
                        throw new IndexOutOfBoundsException("size; " + size + "index: " + index);
                    }
                    node = node.next;
                }
            }
        } else if (index == 0) {
            return head.value;
        } else {
            throw new IndexOutOfBoundsException("size; " + size + "index: " + index);
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = head;
        T oldValue = null;
        if ((size - 1) > index) {
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    oldValue = node.value;
                    node.value = value;
                    break;
                }
                node = node.next;
            }
            return oldValue;
        } else {
            throw new IndexOutOfBoundsException("size; " + size + "index: " + index);
        }

    }

    @Override
    public T remove(int index) {
        Node<T> node = head;
        if (index > 0 && index < (size)) {
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    if (node.next != null) {
                        node.next.prev = node.prev;
                    }
                    node.prev.next = node.next;
                    size--;
                    return node.value;
                } else {
                    node = node.next;
                }
            }
        } else if (index == 0) {
            if (size != 1) {
                node.next.prev = null;
                head = node.next;
                size--;
                return node.value;
            } else {
                head = null;
                tail = null;
                size--;
                return node.value;
            }

        }
        {
            throw new IndexOutOfBoundsException("size; " + size + "index: " + index);
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        if (size == 1) {
            head = null;
            size--;
            return true;
        } else {
            for (int i = 0; i < size; i++) {
                if (node.value == null && object == null) {
                    node.next.prev = node.prev;
                    node.prev.next = node.next;
                    size--;
                    return true;
                } else if (node.value != null) {
                    if (node.value.equals(object)) {
                        if (node.equals(tail)) {
                            node.prev.next = null;
                            tail = node.prev;
                            size--;
                            return true;
                        } else if (node.equals(head)) {
                            head = node.next;
                            head.prev = null;
                            size--;
                            return true;
                        } else {
                            node.prev.next = node.next;
                            node.next.prev = node.prev;
                            size--;
                            return true;
                        }
                    }
                    node = node.next;
                } else {
                    node = node.next;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev,T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
