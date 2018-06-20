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
    private ThreadPool threadPool;//�̳߳�  
    private ServerSocket serverSocket;  
    private final int POOL_SIZE=4;//����Cpuʱ�̳߳��й�������Ŀ  
      
    public ServerSocets()throws IOException{  
        serverSocket=new ServerSocket(port);  
        //�����̳߳�  
        //Rumtime��availableProcessors()�������ص�ǰϵͳ��cup��Ŀ  
        //ϵͳ��cpuԽ�࣬�̳߳��е���ĿҲԽ��  
        threadPool=new ThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);  
        System.out.println("������������");  
    }  
    public void service(){  
        while (true) {  
            Socket socket=null;  
            try {  
                socket=serverSocket.accept();  
                threadPool.execute(new Handler(socket));//����ͻ�ͨѶ�����񽻸��߳�  
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
  
//�����뵥���ͻ�ͨѶ����  
class Handler implements Runnable{  
    private Socket socket;  
    public Handler(Socket socket){  
        this.socket=socket;  
    }  
      
    private PrintWriter getWriter(Socket socket)throws IOException{  
        OutputStream socketOut=socket.getOutputStream();      
        return new PrintWriter(socketOut, true);//����Ϊtrue��ʾÿдһ�У�PrintWriter������Զ������������д��Ŀ��  
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