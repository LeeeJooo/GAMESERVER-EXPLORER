using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using System.Net.Sockets;
using System.Net;
using System.Text;

public class CharacterMove : MonoBehaviour
{
    private TCPClientManager tcpClientManager;

    private TcpClient client;
    private NetworkStream stream;

    private IPAddress serverIp;
    private int serverPort;

    public Transform cameraTransform;
    public CharacterController characterController;

    public float moveSpeed = 3f;
    public float jumpSpeed = 3f;
    public float gravity = -10f;
    public float yVelocity = 0;

    private bool isMoving = false;
    private float sendTimer = 0f;
    private float sendInterval = 0.5f; // 0.5�ʿ� �� ���� ������

    void Start()
    {
        serverIp = IPAddress.Parse(ServerConfigLoader.serverIp);
        serverPort = int.Parse(ServerConfigLoader.serverPort);

        // TCP Ŭ���̾�Ʈ �Ŵ��� �ʱ�ȭ
        tcpClientManager = new TCPClientManager(ServerConfigLoader.serverIp, int.Parse(ServerConfigLoader.serverPort));
        if (tcpClientManager.Connect())
        {
            Debug.Log("TCP ���� ����!");
        }
        else
        {
            Debug.LogError("TCP ���� ����!");
        }

        client = new TcpClient();
        client.Connect(serverIp, serverPort);
        stream = client.GetStream();

        // �÷��̾� �̵� ���� ���� �ʱ�ȭ
        cameraTransform = GetComponentInChildren<Camera>().transform;
        characterController = GetComponent<CharacterController>();
    }

    void Update()
    {
        float h = Input.GetAxis("Horizontal");
        float v = Input.GetAxis("Vertical");

        Vector3 moveDirection = new Vector3(h, 0, v);
        moveDirection = cameraTransform.TransformDirection(moveDirection);
        moveDirection *= moveSpeed;

        if (characterController.isGrounded)
        {
            yVelocity = 0;
            if (Input.GetKeyDown(KeyCode.Space))
            {
                yVelocity = jumpSpeed;
                SendPlayerPosition();
            }
        }

        yVelocity += (gravity * Time.deltaTime);
        moveDirection.y = yVelocity;
        characterController.Move(moveDirection * Time.deltaTime);

        Dash();

        // �̵� Ű�� ���� ������ isMoving ���� true�� ����
        isMoving = (h != 0 || v != 0);

        // 0.5�ʿ� �� ���� �÷��̾� ��ġ�� ����
        sendTimer += Time.deltaTime;
        if (sendTimer >= sendInterval && isMoving)
        {
            SendPlayerPosition();
            sendTimer = 0f; // Ÿ�̸� �ʱ�ȭ
        }
    }

    void Dash()
    {
        if (!Input.GetKeyDown(KeyCode.S))
        {
            if (Input.GetKeyDown(KeyCode.LeftShift))
            {
                moveSpeed += 10f;
            }

            if (Input.GetKeyUp(KeyCode.LeftShift))
            {
                moveSpeed -= 10f;
            }
        }
    }

    void SendPlayerPosition()
    {
        string playerPosition = transform.position.ToString();
        string playerRotation = cameraTransform.rotation.ToString();
        //Debug.Log(playerPosition);
        //Debug.Log(playerRotation);

        // �÷��̾��� ���� ��ġ �� ȸ�� ������ JSON���� ��ȯ
        string json = "{\"type\":\"ingame\",\"event\":\"moving\",\"mapId\":0,\"channelId\":1,\"userId\":1," +
            "\"posX\":" + transform.position.x + ",\"posY\":" + transform.position.y + ",\"posZ\":" + transform.position.z +
            ",\"rotX\":" + transform.rotation.eulerAngles.x + ",\"rotY\":" + transform.rotation.eulerAngles.y +
            ",\"rotZ\":" + transform.rotation.eulerAngles.z + "}";

        // JSON�� ������ ����
        byte[] data = Encoding.UTF8.GetBytes(json);
        stream.Write(data, 0, data.Length);
    }

    // ��������
    private void OnDestroy()
    {
        tcpClientManager.Disconnect();
    }

}

