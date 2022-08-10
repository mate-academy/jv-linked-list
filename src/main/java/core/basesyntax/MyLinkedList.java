package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    //будем сохранять ссылку на первый элемент
    private Node<T> head;
    //будем сохранять ссылку на последний элемент
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        //инициализируем при создании
        tail = new Node<>(null, null, null);
        head = new Node<>(null, null, null);
    }

    @Override
    //при вызове метода add() создаем новый нод
    // c переданным значением и его нужно прицепить к LinkedList
    public void add(T value) {
        //полюбому каждый раз у нас будет новый нод
        Node<T> node = new Node<>(null, value, null);
        //теперь этот новый узел нужно прицепить в конец LinkedList
        //установив в бывший последний элемент
        // ссылку next на наш новый нод -> node

        //если LinkedList пустой то связным ссылкам даем налл
        if (size == 0) {
            tail = head = node;//у нас внутри нода next и prev наллы
        } else {
            //если нод не первый то в бывшем последнем элементе
            //нужно засетить переменную next на новый последний элемент
            tail.next = node;
            //а нашему новому последнему ноду нужно засетить
            //значение prev на бывший последний нод, потому что
            //LinkedList двусвязный
            node.prev = tail;
            //и хвостом у нас становится наш новый элемент
            tail = node;
        }
        //элемент добавили, физический сайз +1
        size++;


    }

    @Override
    //тут также нужно создать новый нод,
    //но цеплять его придется в различные места,
    //а не только в конец(середина, начало, конец)
    //во всех этих ситуациях будет разная логика
    public void add(T value, int index) {
        //прежде всего нужно добавить проверку валидный ли индекс
        checkIndex(index);
        //при любом добавлении в LinkedList это по любому новый нод
        Node<T> node = new Node<>(null, value, null);
        //если LinkedList сейчас пустой, то установить значение
        //мы можем только в первый элемент
        //и тогда снова tail и head будут равны null
        if (head == null) {
            head = tail = node;
        } else if (index == 0) {
            //у нашего добавляемого нода в ссылку next
            //пишем наш предыдущий первый нод
            node.next = head;
            //теперь бывшему первому ноду в ссылку prev добавляем
            //наш новый node
            head.prev = node;
            //затем ссылке head присваиваем наш новый node
            //он стал первым в LinkedList
            head = node;
        } else if (index == size) {
            //сценарий когда нам нужно добавить элемент в самый конец
            //нам наш только что созданный нод пока что нужно
            //просто прицепить на последнюю позицию в LinkedList
            //у текущего последнего элемента сетим в next ссылку
            //наш новый нод
            tail.next = node;
            //у нового нода поле prev наполняем бывшим последним нодом
            node.prev = tail;
            //и только что созданный нод делаем последним
            tail = node;
        } else {
            //а теперь логика как добавлять в середину LinkedList
            //нужно дойти до индекса который будет стоять ПЕРЕД местом
            //куда нам нужно добавить новый элемент и установить
            //переопределив ссылки, разрываем цепь, вставляем элемент, соединяем.
            //создадим референс на первый элемент с какого стартуем
            Node<T> current = head;
            //для того что бы дойти до нужного элемента юзай fori до index
            for (int i = 0; i < index; i++) { //до элемента, который стоит перед местом куда будем вставлять
                //на каждой итерации будем перепрыгивать наши ноды и дойдем до нашего нода
                current = current.next;
            }
            //теперь работаем со ссылками
            //цепляем вторую часть нашего "хвоста" в наш нод
            //установив ему next на next нашего пойманного нода current
            node.next = current.next;
            //и по идее тут мы привязали к элементу перед нашим нодом
            node.prev = current;
        }
        //и поскольку мы добавили элемент то
        size++;

    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private Node<T> findNodeByIndex(int index) {
        return new Node<>(null, null, null);
    }

    private void addFirstNode(Node<T> node) {
        head = node;
        tail = node;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private void checkEqualsIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private class Node<T> {
        //переменная со ссылкой на след элемент
        private Node<T> next;
        //переменная со ссылкой на посл элемент
        private Node<T> prev;
        //переменная со значением, которое хотим сохранить
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
