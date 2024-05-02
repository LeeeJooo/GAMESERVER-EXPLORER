using System;
using System.Net.Sockets;
using UnityEngine;

public class TCPClientManager : MonoBehaviour
{
    private static TCPClientManager instance;

    private TcpClient client;
    private NetworkStream stream;
    private string serverIp;
    private int serverPort;

    private void Awake()
    {
        // TCPClientManager �̱��� �ν��Ͻ� ����
        if (instance == null)
        {
            instance = this;
            // �ٸ� ������ �̵��ص� ����
            DontDestroyOnLoad(gameObject);
        }
        else
        {
            // �̹� �ν��Ͻ��� �ִ� ��� �ߺ� ������ ���� �ı�
            Destroy(gameObject); 
        }
    }

    public static TCPClientManager Instance
    {
        get
        {
            // �ν��Ͻ��� ���� ��� ���� ����
            if (instance == null)
            {
                GameObject obj = new GameObject("TCPClientManager");
                instance = obj.AddComponent<TCPClientManager>();
            }
            return instance;
        }
    }

    public void Init(string ip, int port)
    {
        serverIp = ip;
        serverPort = port;
    }

    public bool Connect()
    {
        try
        {
            client = new TcpClient(serverIp, serverPort);
            stream = client.GetStream();
            Debug.Log("���� ����!");
            return true;
        }
        catch (Exception e)
        {
            Debug.LogError("���� ����: " + e.Message);
            return false;
        }
    }

    public void Disconnect()
    {
        if (client != null)
        {
            client.Close();
            Debug.Log("���� ����");
        }
    }

    public NetworkStream GetStream()
    {
        return stream;
    }

    public void SendTCPRequest(string request)
    {
        try
        {
            byte[] requestData = System.Text.Encoding.UTF8.GetBytes(request);
            stream.Write(requestData, 0, requestData.Length);
        }
        catch (Exception e)
        {
            Debug.LogError("TCP ��û ������ �� ���� �߻�: " + e.Message);
        }
    }
}
