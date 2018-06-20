package com.cheng.service;

import java.util.LinkedList;  

public class ThreadPool extends ThreadGroup{  
    private boolean isClosed=false;//�̳߳��Ƿ�ر�  
    private LinkedList<Runnable> workQueue;//��ʾ��������  
    private static int threadPoolID;//��ʾ�̳߳�ID  
    private int threadID;//��ʾ�����߳�ID  
      
    public ThreadPool(int poolSize){//poolSizeָ���̳߳��еĹ����߳���  
        super("ThreadPool-"+(threadPoolID++));  
        setDaemon(true);  
        workQueue=new LinkedList<Runnable>();//���������������߳�  
        for(int i=0;i<poolSize;i++){  
            new WorkThread().start();  
              
        }     
          
    }  
    /** 
     *���������м���һ���������ɹ����߳�ȥִ�� 
     * @param task 
     */  
    public synchronized void execute(Runnable task){  
        if(isClosed){//�̳߳ر��ر����׳�  
            throw new  IllegalStateException();  
        }  
        if(task!=null){  
            workQueue.add(task);  
            notify();//��������getTask()�����еȴ�����Ĺ����߳�  
        }  
          
    }  
      
    /** 
     * �ӹ���������ȡ��һ�����񣬹����̻߳���ô˷��� 
     * @return 
     * @throws InterruptedException 
     */  
      
    protected synchronized Runnable getTask()throws InterruptedException{  
        while (workQueue.size()==0) {  
            if(isClosed)return null;  
            wait();//�������������û�����񣬾͵ȴ�����  
              
        }  
        return workQueue.removeFirst();  
          
    }  
  
    /** 
     * �ر��̳߳� 
     */  
    public synchronized void close(){  
        if(!isClosed){  
            isClosed=true;  
            workQueue.clear();//clean a workQueue  
            interrupt();//stop all of thread pool ID  
        }  
          
    }  
      
    /** 
     * �ȴ������̰߳���������ִ���� 
     */  
    public void join(){  
        synchronized (this) {  
            isClosed=true;  
            notifyAll();//���ѻ���getTask()�����еȴ�����Ĺ����߳�  
              
        }  
          
        Thread[]thread=new Thread[activeCount()];  
        int count =enumerate(thread);//��õ�ǰ���л��ߵĹ����߳�  
        for (int i = 0; i <count; i++) {//�ȴ����е��߳���������  
            try {  
                thread[i].join();//�ȴ����й����߳����н���  
            } catch (InterruptedException e) {  
                // TODO: handle exception  
            }  
              
        }  
          
          
    }  
    /** 
     * �����߳� 
     * 
     * @author Mouse 
     * 
     */  
    private class WorkThread extends Thread{  
        public WorkThread(){  
            //���뵽��ǰTreadPool�߳�����  
            super(ThreadPool.this,"WorkThread-"+(threadID++));  
              
        }  
        public void run(){  
            while (!isInterrupted()) {//�ж��߳��Ƿ��ж�  
                Runnable task=null;  
                try {//ȡ������  
                    task=getTask();  
                } catch (InterruptedException e) {  
                    // TODO: handle exception  
                }  
                if(task==null)return ;//���getTask()����Null�����߳�ִ��getTask()���жϣ���������߳�  
                try {//��������  
                    task.run();  
                } catch (Throwable e) {  
                    // TODO: handle exception  
                    e.printStackTrace();  
                }  
                  
            }  
        }  
    }  
      
  
}  
