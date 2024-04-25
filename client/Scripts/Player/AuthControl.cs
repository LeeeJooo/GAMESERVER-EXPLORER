using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using UnityEngine.SceneManagement;
using System.Text.RegularExpressions;

public class AuthControl : MonoBehaviour
{
    [Header ("LoginPage")]
    public GameObject loginPage;// �ʱ� �α��� ȭ�� ������Ʈ
    public GameObject signPage;// ȸ�������� �������� ��Ÿ���� ������Ʈ
    public GameObject ChannelChoose; // �α��� �Ŀ� ���̴� ä�� ���
    public GameObject title;
    public Button SignUpBtn;
    public Button SignUpYesBTN;
    public Button SignUpNoBTN;
    public Button idCheckBTN;
    public Button nickNameCheckBTN;
    public Button LoginBTN;


    [Header ("LoginField")]
    public TMP_InputField loginID;
    public TMP_InputField loginPW;

    [Header ("SignupField")]
    public TMP_InputField signUpId;
    public TMP_InputField signUpnickname;
    public TMP_InputField signUpPw;
    public TMP_InputField signUpPwChenk;
    public TextMeshProUGUI signUpInfoText;

    [Header("ProfilePage")]
    public GameObject ProfilePage;
    public GameObject ProfileChanger;
    public Image ProfileImage;
    public Button ProfileDesignLeftBtn;
    public Button ProfileDesignRightBtn;
    public TextMeshProUGUI NowNickName;
    public TextMeshProUGUI NickNameChange;
    public Button ProfileChangeBtn;
    public Button ProfileChangeAcceptBtn;
    public Button endingPlanetBtn;


    [Header("RoomChannelSelect")]
    public GameObject ExistRoomOBJ;
    public GameObject NoneRoomOBJ;
    public GameObject NewMakeChannel;
    public GameObject JoinNewChannel;
    public Button ExistChannelBtn;
    public Button ExistChannelJoinBtn;
    public Button ExistChannelDeleteBtn;
    public Button NoneChannelBtn;
    public Button NewChannelJoinBtn;
    public Button JoinOkBtn;
    public Button JoinCancelBtn;
    public Button MakeOkBtn;
    public Button MakeCancelBtn;
    public Button MakeNewChannelBtn;



    // Start is called before the first frame update
    private void Start()
    {
        SignUpBtn.onClick.AddListener(GoSignUp);
        SignUpYesBTN.onClick.AddListener(() => SignUp());
        SignUpNoBTN.onClick.AddListener(SignUpCancel);
        LoginBTN.onClick.AddListener(GoChanel);
        ProfileChangeBtn.onClick.AddListener(EditProfile);
        ProfileChangeAcceptBtn.onClick.AddListener(EditAccept);
        ExistChannelBtn.onClick.AddListener(IfExistRoom);
        NoneChannelBtn.onClick.AddListener(IfNoneRoom);
        //ExistChannelJoinBtn.onClick.AddListener(IfNoneRoom);
        //ExistChannelDeleteBtn.onClick.AddListener(IfNoneRoom);
        NewChannelJoinBtn.onClick.AddListener(JoinAnotherRoom);
        JoinOkBtn.onClick.AddListener(JoinOk);
        idCheckBTN.onClick.AddListener(validId);
        nickNameCheckBTN.onClick.AddListener(validNickName);
        JoinCancelBtn.onClick.AddListener(JoinCancel);
        MakeNewChannelBtn.onClick.AddListener(MakeAnotherRoom);
        MakeOkBtn.onClick.AddListener(MakeOk);
        MakeCancelBtn.onClick.AddListener(MakeCancel);
        title.SetActive(true);
        loginPage.SetActive(true);
        loginPW.contentType = TMP_InputField.ContentType.Password;
        signUpPw.contentType = TMP_InputField.ContentType.Password;
        signUpPwChenk.contentType = TMP_InputField.ContentType.Password;
        signUpPwChenk.interactable = false;
        signUpPw.interactable = false;
        nickNameCheckBTN.interactable = false;
        signUpnickname.interactable = false;
    }

    public void CancelLogin()
    {
        // ȸ������ ��� �� �Է� �ʵ� �� �ǵ�� �ؽ�Ʈ �ʱ�ȭ
        loginID.text = "";
        loginPW.text = "";
    }
    public void CancelSignUp()
    {
        // ȸ������ ��� �� �Է� �ʵ� �� �ǵ�� �ؽ�Ʈ �ʱ�ȭ
        signUpId.text = "";
        signUpnickname.text = "";
        signUpPw.text = "";
        signUpPwChenk.text = "";
        signUpInfoText.text = "";
    }
    public void EditProfile()
    {
        ProfileChanger.SetActive(true);
        ProfilePage.SetActive(false);
    }

    public void EditAccept()
    {
        ProfileChanger.SetActive(false);
        ProfilePage.SetActive(true);
    }

    public void GoSignUp()
    {
        CancelLogin();
        loginPage.SetActive(false);
        signPage.SetActive(true);


    }
    bool CheckOkPw(string password)
    {
        // ������, ����, Ư������ �߿��� �ּ� �ϳ����� �����ϸ�, ���̰� 8���� 15 ������ ���ڿ��� ��Ÿ���� ���Խ�
        string pattern = @"^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,15}$";

        // ���Խİ� ��ġ�Ǵ��� Ȯ��
        if (Regex.IsMatch(password, pattern))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public void SignUp()
    {

        string password = signUpPw.text;
        string passwordConfirm = signUpPwChenk.text;

        // ��ȿ�� �˻�
        if (CheckOkPw(password))
        {
            if (password == passwordConfirm)
            {
                Debug.Log("ȸ������ ����");
                CancelSignUp();
                signPage.SetActive(false);
                loginPage.SetActive(true);
            }
            else
            {
                signUpInfoText.text = "��й�ȣ�� ��й�ȣ Ȯ���� ��ġ���� �ʽ��ϴ�.";
            }
        }
        else
        {
            // ��ȿ���� ���� ��й�ȣ�Դϴ�.
            Debug.Log("��й�ȣ�� ���ĺ�, ����,@$!%*?&�� ���� 1�� �̻� �����ؾ��մϴ�. ");
            signUpInfoText.text = "��й�ȣ�� ���ĺ�, ����, Ư������(@$!%*?&)�� ���� 1�� �̻� �����ؾ��մϴ�.";
        }
    }

    public void SignUpCancel()
    {
        CancelSignUp();
        Debug.Log("�̰� ĵ�����ξ� ������");
        signPage.SetActive(false);
        loginPage.SetActive(true);
    }

    public void GoChanel()
    {
        loginPage.SetActive(false);
        title.SetActive(false);
        ChannelChoose.SetActive(true);
    }


    public void IfExistRoom()
    {
        Debug.Log("ExistOK");
        NoneRoomOBJ.SetActive(false);
        ExistRoomOBJ.SetActive(true);
    }
    public void IfNoneRoom()
    {
        Debug.Log("NoneOk");
        ExistRoomOBJ.SetActive(false);
        NoneRoomOBJ.SetActive(true);
    }

    public void JoinAnotherRoom()
    {
        JoinNewChannel.SetActive(true);
    }

    public void JoinOk()
    {
        JoinNewChannel.SetActive(false);
        SceneManager.LoadScene("GameRoom");
    }

    public void JoinCancel()
    {
        JoinNewChannel.SetActive(false);
    }

    public void MakeAnotherRoom()
    {
        NewMakeChannel.SetActive(true);
    }

    public void MakeOk()
    {
        NewMakeChannel.SetActive(false);
        SceneManager.LoadScene("GameRoom");
    }

    public void MakeCancel()
    {
        NewMakeChannel.SetActive(false);
    }


    bool CheckOkId(string id)
    {
        string pattern = @"^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,15}$";

        // ���Խİ� ��ġ�Ǵ��� Ȯ��
        if (Regex.IsMatch(id, pattern))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void validId()
    {
        string id = signUpId.text;
        // ��ȿ�� �˻�
        if (CheckOkId(id))
        {
            //if (id == passwordConfirm)
            //{
                Debug.Log("���̵� ���Ǹ���");
                signUpInfoText.text = "";
                nickNameCheckBTN.interactable = true;
                signUpnickname.interactable = true;
                signUpId.interactable = false;
                idCheckBTN.interactable = false;
            //}
            //else
            //{
            //    signUpInfoText.text = "���̵� �ߺ��˴ϴ�.";
            //}
        }
        else
        {
            // ��ȿ���� ���� id�Դϴ�.
            Debug.Log("id�� ����,���ڰ� ���� 1�� �̻� ���Ե� 6 ���� �̻� 15 ���� �����Դϴ�. ");
            signUpInfoText.text = "id�� ����,���ڰ� ���� 1�� �̻� ���Ե� 6 ���� �̻� 15 ���� �����Դϴ�. ";
            
        }
    }

    bool CheckOkNickName(string nickname)
    {
        string pattern = @"^[A-Za-z0-9��-�R]{2,8}$";

        // ���Խİ� ��ġ�Ǵ��� Ȯ��
        if (Regex.IsMatch(nickname, pattern))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void validNickName()
    {
        string nickname = signUpnickname.text;
        // ��ȿ�� �˻�
        if (CheckOkNickName(nickname))
        {
            //if (id == passwordConfirm)
            //{
            Debug.Log("�г��� ���Ǹ���");
            signUpInfoText.text = "";
            signUpPwChenk.interactable = true;
            signUpPw.interactable = true;
            signUpnickname.interactable = false;
            nickNameCheckBTN.interactable = false;
            //}
            //else
            //{
            //    signUpInfoText.text = "�г����� �ߺ��˴ϴ�.";
            //}
        }
        else
        {
            // ��ȿ���� ���� id�Դϴ�.
            Debug.Log("�г����� 2-8 ���� ������ ����, ����, �ѱ۷θ� �̷�����ϴ�.");
            signUpInfoText.text = "�г����� 2-8 ���� ������ ����, ����, �ѱ۷θ� �̷�����ϴ�.";
            signUpPwChenk.interactable = false;
            signUpPw.interactable = false;
        }
    }
}





