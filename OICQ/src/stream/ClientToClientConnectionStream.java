package stream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientToClientConnectionStream {

	 private DatagramPacket userReceivePacket;
	    private DatagramSocket userDataSocket;
	    private byte buffer[];
	    private InetAddress hostAddress;
	    private int port;

	    public ClientToClientConnectionStream(DatagramSocket userDataSocket)
	    {
	        super();
	        this.userDataSocket = userDataSocket;
	        buffer = new byte[1024];
	        userReceivePacket = new DatagramPacket(buffer, buffer.length);
	    }

	    public ClientToClientConnectionStream(DatagramSocket userDataSocket, InetAddress hostAddress, int port)
	    {
	        super();
	        this.hostAddress = hostAddress;
	        this.port = port;
	        this.userDataSocket = userDataSocket;
	        buffer = new byte[1024];
	        userReceivePacket = new DatagramPacket(buffer, buffer.length);
	    }

	    public void send(String message)
	    {
	        try
	        {
	            userDataSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, hostAddress, port));
	        } catch (IOException e)
	        {
	            System.out.println("UDP端口被占用！");
	            e.printStackTrace();
	        }
	    }

	    public String read()
	    {
	        try
	        {
	            if (!userDataSocket.isClosed())
	            {
	                userDataSocket.receive(userReceivePacket);
	                System.out.print("liu现在端口号：" + userReceivePacket.getPort());
	                String string = new String(userReceivePacket.getData(), 0, userReceivePacket.getLength(), "GBK");
	                return string;
	            }
	        } catch (UnsupportedEncodingException e)
	        {
	            System.out.println("不支持的编码类型！");
	            e.printStackTrace();
	        } catch (IOException e)
	        {
	            System.out.println(userDataSocket == null);
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public void close()
	    {
	        if (userDataSocket != null)
	        {
	            userDataSocket.close();
	        }
	    }

	    public void setPort(int port)
	    {
	        this.port = port;
	    }

	    public String getHostAddress()
	    {
	        return hostAddress.getHostAddress();
	    }

	    public void setHostAddress(InetAddress hostAddress)
	    {
	        this.hostAddress = hostAddress;
	    }

	    public int getPort()
	    {
	        return port;
	    }

	    public DatagramPacket getUserReceivePacket()
	    {
	        return userReceivePacket;
	    }

	    public InetAddress getInetAddress()
	    {
	        return userReceivePacket.getAddress();
	    }

	    public DatagramSocket getDatagramSocket()
	    {
	        return userDataSocket;
	    }

}
