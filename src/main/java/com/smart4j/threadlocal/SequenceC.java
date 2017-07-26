package com.smart4j.threadlocal;

/**
 * Created by Administrator on 2017/7/25.
 */
public class SequenceC implements Sequence {

    private static MyThreadlocal<Integer> numberContainer=new MyThreadlocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    @Override
    public int getNumber() {
        numberContainer.set(numberContainer.get()+1);
        return numberContainer.get();
    }

    public static void main(String[] args) {
        Sequence sequence=new SequenceC();
        ClientThread t1 = new ClientThread(sequence);
        ClientThread t2 = new ClientThread(sequence);
        ClientThread t3 = new ClientThread(sequence);
        t1.start();
        t2.start();
        t3.start();
    }
}
