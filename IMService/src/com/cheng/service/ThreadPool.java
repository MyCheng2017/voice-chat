package com.cheng.service;

import java.util.LinkedList;  

public class ThreadPool extends ThreadGroup{  
    private boolean isClosed=false;//线程池是否关闭  
    private LinkedList<Runnable> workQueue;//表示工作队列  
    private static int threadPoolID;//表示线程池ID  
    private int threadID;//表示工作线程ID  
      
    public ThreadPool(int poolSize){//poolSize指定线程池中的工作线程数  
        super("ThreadPool-"+(threadPoolID++));  
        setDaemon(true);  
        workQueue=new LinkedList<Runnable>();//创建并启动工作线程  
        for(int i=0;i<poolSize;i++){  
            new WorkThread().start();  
              
        }     
          
    }  
    /** 
     *向工作队列中加入一个新任务，由工作线程去执行 
     * @param task 
     */  
    public synchronized void execute(Runnable task){  
        if(isClosed){//线程池被关闭则抛出  
            throw new  IllegalStateException();  
        }  
        if(task!=null){  
            workQueue.add(task);  
            notify();//唤醒正在getTask()方法中等待任务的工作线程  
        }  
          
    }  
      
    /** 
     * 从工作队列中取出一个任务，工作线程会调用此方法 
     * @return 
     * @throws InterruptedException 
     */  
      
    protected synchronized Runnable getTask()throws InterruptedException{  
        while (workQueue.size()==0) {  
            if(isClosed)return null;  
            wait();//如果工作队列中没有任务，就等待任务  
              
        }  
        return workQueue.removeFirst();  
          
    }  
  
    /** 
     * 关闭线程池 
     */  
    public synchronized void close(){  
        if(!isClosed){  
            isClosed=true;  
            workQueue.clear();//clean a workQueue  
            interrupt();//stop all of thread pool ID  
        }  
          
    }  
      
    /** 
     * 等待工作线程把所有任务执行完 
     */  
    public void join(){  
        synchronized (this) {  
            isClosed=true;  
            notifyAll();//唤醒还在getTask()方法中等待任务的工作线程  
              
        }  
          
        Thread[]thread=new Thread[activeCount()];  
        int count =enumerate(thread);//获得当前所有或者的工作线程  
        for (int i = 0; i <count; i++) {//等待所有的线程运行起来  
            try {  
                thread[i].join();//等待所有工作线程运行结束  
            } catch (InterruptedException e) {  
                // TODO: handle exception  
            }  
              
        }  
          
          
    }  
    /** 
     * 工作线程 
     * 
     * @author Mouse 
     * 
     */  
    private class WorkThread extends Thread{  
        public WorkThread(){  
            //加入到当前TreadPool线程组中  
            super(ThreadPool.this,"WorkThread-"+(threadID++));  
              
        }  
        public void run(){  
            while (!isInterrupted()) {//判断线程是否被中断  
                Runnable task=null;  
                try {//取出任务  
                    task=getTask();  
                } catch (InterruptedException e) {  
                    // TODO: handle exception  
                }  
                if(task==null)return ;//如果getTask()返回Null或者线程执行getTask()被中断，则结束此线程  
                try {//运行任务  
                    task.run();  
                } catch (Throwable e) {  
                    // TODO: handle exception  
                    e.printStackTrace();  
                }  
                  
            }  
        }  
    }  
      
  
}  
