using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ItemInfo : MonoBehaviour
{
    // �ٸ� Ŭ�������� �������� ������ �ٰ��ü� �ֵ��� ����
    public static ItemInfo istance;

    private void Awake()
    {
        istance = this;
    }

    public List<Item> itemDB = new List<Item>();
}
