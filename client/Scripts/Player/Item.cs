using System.Collections;
using System.Collections.Generic;
using UnityEngine;
// ������ Ÿ���� ����Ʈ�� ���� Ÿ���� �������ش�.
public enum ItemType
{
    //���
    Equipment,
    //�Һ�
    Consumable,
    //��Ÿ
    Etc,
}

[System.Serializable]
public class Item
{
    //�������� �̸�, ����, Ÿ���� ������ ����
    // �������� �̸�
    public string itemName;
    // �������� ����
    public Sprite itemImage;
    // �������� Ÿ�� 
    // Ÿ���� �˾ƺ� �� �ִ°� ���� �����ϱ� ������ � ���������� ����Ʈ�� ����Ʈ�� �ִ� ���߿��� ����
    public ItemType itemtypes;

    public bool Use()
    {
        //��밡���� ���������� �Ǻ��Ͽ� ����Ѵ�.
        return false;
    }
}
