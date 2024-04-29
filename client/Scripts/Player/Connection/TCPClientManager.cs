using System;
using System.Net.Sockets;
using UnityEngine;

public class TCPClientManager
{
    private TcpClient client;
    private string serverIp;
    private int serverPort;


    public TCPClientManager(string ip, int port)
    {
        serverIp = ip;
        serverPort = port;
    }

    public bool Connect()
    {
        try
        {
            client = new TcpClient(serverIp, serverPort);
           
            return true;
        }
        catch (Exception e)
        {
            Console.WriteLine("�����߻� �ФФФ� ", e.Message);
            return false;
        }
        
    }

    public void Disconnect() 
    {
        if(client != null) 
        {
            client.Close();
            Console.WriteLine("���� ���� !!! ");
        }
    }
}
