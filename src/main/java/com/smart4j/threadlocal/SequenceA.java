package com.smart4j.threadlocal;

/**
 * Created by Administrator on 2017/7/25.
 */
public class SequenceA implements Sequence {
    private static int number=0;
    public int getNumber(){
        number++;
        return number;
    }

    public static void main(String[] args) {
        Sequence sequence=new SequenceA();
        ClientThread t1 = new ClientThread(sequence);
        ClientThread t2 = new ClientThread(sequence);
        ClientThread t3 = new ClientThread(sequence);
        t1.start();
        t2.start();
        t3.start();

    }
}
