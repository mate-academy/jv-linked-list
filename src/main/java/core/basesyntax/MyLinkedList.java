package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    T item;
    MyLinkedList<T> next;
    MyLinkedList<T> prev;
    transient MyLinkedList<T> first;
    transient MyLinkedList<T> last;
    MyLinkedList() {
    }
    MyLinkedList(MyLinkedList<T> prev, T element, MyLinkedList<T> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
        //this.last = this;
        //this.first = this;

    }
    @Override
    public void add(T value) {
        if (isEmpty()) {
            MyLinkedList newNode = new MyLinkedList(null, value, null);
            first = newNode;
            last = newNode;
        } else {
            MyLinkedList newNode = new MyLinkedList(last, value, null);
            last.next = newNode;
            last = newNode;
        }
    }

    @Override
    public void add(T value, int index) throws IndexOutOfBoundsException  {
        if ((index > size()) || (index<0)) {
            throw new IndexOutOfBoundsException("Index dosn't much");
        }
        if (index == size()) {
            add(value);
            return;
        }
        if (index == 0) {
            MyLinkedList newNode = new MyLinkedList(null, value, first);
            first.prev = newNode;
            first = newNode;
            return;
        }
        int i = 0;
        MyLinkedList node = first;
        while (i != index){
            node = node.next;
            i++;
        }
        MyLinkedList newNode = new MyLinkedList(node.prev, value, node);
        node.next = newNode;
        node.prev = newNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++){
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if ((index >= size()) || (index<0)) {
            throw new IndexOutOfBoundsException("Index dosn't much");

        }
        int i = 0;
        MyLinkedList node = first;
        while (i != index){
            node = node.next;
            i++;
        }
        return (T) node.item;
    }

    @Override
    public T set(T value, int index) throws IndexOutOfBoundsException {
        if ((index >= size()) || (index<0)) {
            throw new IndexOutOfBoundsException("Index dosn't much");
        }
        int i = 0;
        MyLinkedList node = first;
        while (i != index){
            node = node.next;
            i++;
        }
        node.item = value;
        return value;
    }

    @Override
    public T remove(int index)  throws IndexOutOfBoundsException {
        if ((index >= size()) || (index<0)) {
            throw new IndexOutOfBoundsException("Index dosn't much");
        }
        int i = 0;
        MyLinkedList node = first;
        while (i != index){
            node = node.next;
            i++;
        }
        if (i == 0) {
            node.next.prev = null;
            node.first = node.next;
            return (T) node.item;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        } else {
            node.prev.next = null;
            last = node.prev;
        }
        return (T) node.item;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()){
            return false;
        }
        MyLinkedList node = first;
        while (node != null){
            if (node.item.equals(object) ){
                if (node == first) {
                    if (node.next != null) {
                        node.next.prev = null;
                        node.first = node.next;
                    }
                }

                if (node.next != null) {
                    node.next.prev = node.prev;
                    node.prev.next = node.next;
                } else {
                    node.prev.next = null;
                    node.last = node.prev;
                }
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        if (isEmpty()){
            return 0;
        }
        int i = 1;
        MyLinkedList node = first;
        while (node.next != null){
            node = node.next;
            i++;
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        if (first == null) {
            return true;
        }
        return false;
    }
}
