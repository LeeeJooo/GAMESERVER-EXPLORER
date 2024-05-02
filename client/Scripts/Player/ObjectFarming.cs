using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net.Sockets;
using System.IO;
using Newtonsoft.Json;

// �Ĺ� ������ ���� Ŭ����
public class FarmObject
{
    public string type;
    public string eventType;
    public int mapId;
    public int cid;
    public int userId;
    public float posX;
    public float posY;
    public float posZ;

    public FarmObject(string type, string eventType, int mapId, int cid, int userId, float posX, float posY, float posZ)
    {
        this.type = type;
        this.eventType = eventType;
        this.mapId = mapId;
        this.cid = cid;
        this.userId = userId;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
}



public class ObjectFarming : MonoBehaviour
{
    private TCPClientManager tcpClientManager;
    private bool isFarming = false; // �Ĺ� ���� ���� üũ
    private ProgressBarControl progressBar;

    public void Awake()
    {
        //tcpClientManager = TCPClientManager.Instance;
        //if (tcpClientManager == null)
        //{
        //    Debug.LogError("TCPClientManager�� �ʱ�ȭ���� �ʾҽ��ϴ�.");
        //    return;
        //}

    }

    public void OjectFarm(RaycastHit hit, Vector3 position)
    {

        if (isFarming)
        {
            return;
        }
        //if (tcpClientManager == null)
        //{
        //    Debug.LogError("TCPClientManager�� �������� �ʾҽ��ϴ�.");
        //    return;
        //}

        //// TCPClientManager�� GetStream �޼��带 ����Ͽ� NetworkStream ��������
        //NetworkStream stream = tcpClientManager.GetStream();
        //if (stream == null)
        //{
        //    Debug.LogError("TCPClientManager�� NetworkStream�� �������� �ʽ��ϴ�.");
        //    return;
        //}

        // �Ĺ� ��û ����
        //FarmObject canfarming = new FarmObject("ingame", "event", 0, 1, 2, position.x, position.y, position.z);
        //string json = JsonConvert.SerializeObject(canfarming);
        //// JSON ������ ����
        //tcpClientManager.SendTCPRequest(json);

        // �����κ��� �Ĺ� ���� ��ȯ ��, �Ĺ� �ð� �޾Ƽ� ���α׷����� ����
        // hit�� ������Ʈ���� ProgressBarControl ������Ʈ ã��
        progressBar = hit.collider.GetComponent<ProgressBarControl>();
        if (progressBar == null)
        {
            Debug.LogError("ProgressBarControl ������Ʈ�� ã�� �� �����ϴ�.");
            return;
        }
        progressBar.StartProgress(10);
        isFarming = true;


    }

    public void FinishFarming()
    {
        // ���� ��� �Ĺ� �Ϸ�, �Ĺ� ������Ʈ ���� ����
        Debug.Log("�ĹֿϷ�~~~~~");
        isFarming = false;
    }

    public void CancleFarming()
    {
        if (isFarming && progressBar != null)
        {
            progressBar.StopProgress();
            isFarming = false;
        }
    }
}
