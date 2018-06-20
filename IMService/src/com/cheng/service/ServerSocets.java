package com.cheng.service;  
  
import java.io.*;  
import java.net.*;  
/** 
 *  
 * @author Mouse 
 * 
 */  
  
public class ServerSocets{  
    private int port=8729;  
    private ThreadPool threadPool;//线程池  
    private ServerSocket serverSocket;  
    private final int POOL_SIZE=4;//单个Cpu时线程池中工作的数目  
      
    public ServerSocets()throws IOException{  
        serverSocket=new ServerSocket(port);  
        //创建线程池  
        //Rumtime的availableProcessors()方法返回当前系统的cup数目  
        //系统的cpu越多，线程池中的数目也越多  
        threadPool=new ThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);  
        System.out.println("服务器启动！");  
    }  
    public void service(){  
        while (true) {  
            Socket socket=null;  
            try {  
                socket=serverSocket.accept();  
                threadPool.execute(new Handler(socket));//把与客户通讯的任务交给线程  
            } catch (Exception e) {  
                // TODO: handle exception  
                e.printStackTrace();  
            }  
              
              
        }  
          
    }  
      
    public static void main(String args[])throws IOException{  
      
            new ServerSocets().service();  
      
          
    }  
      
  
}  
  
//负责与单个客户通讯任务  
class Handler implements Runnable{  
    private Socket socket;  
    public Handler(Socket socket){  
        this.socket=socket;  
    }  
      
    private PrintWriter getWriter(Socket socket)throws IOException{  
        OutputStream socketOut=socket.getOutputStream();      
        return new PrintWriter(socketOut, true);//参数为true表示每写一行，PrintWriter缓存就自动溢出，把数据写到目的  
    }  
      
    private BufferedReader getReader(Socket socket)throws IOException {  
        InputStream socketIn=socket.getInputStream();  
        return new BufferedReader(new InputStreamReader(socketIn));  
          
    }  
    public String echo(String msg){  
        return "echo:"+msg;  
          
    }  
    public void run(){  
        try {  
        	 System.out.println("New connection accepted"+socket.getInetAddress()+":"+socket.getPort()); 
        	 BufferedReader br=getReader(socket);  
            PrintWriter pw=getWriter(socket); 
            String msg=br.readLine();
           while (true) {
			
           try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            System.err.println(msg+"-->sss");
            pw.println(echo(msg));
           if(msg!=null&&msg.endsWith("bye")){  
                	 
                    break;  
                }  
				
			
			
		} 
           
             
            
          
        } catch (IOException e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }finally{  
            try {  
                if(socket!=null){  
                    socket.close();  
                }  
                  
            } catch (IOException  e) {  
                // TODO: handle exception  
                e.printStackTrace();  
            }  
        }  
    }  
}  