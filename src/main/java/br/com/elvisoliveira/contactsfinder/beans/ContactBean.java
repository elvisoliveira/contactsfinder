package br.com.elvisoliveira.contactsfinder.beans;

public class ContactBean
{

    private String Id;
    private String Name;
    private String Address;
    private String Link;
    private String Phone;
    private String City;
    private String Province;
    private String Status;
    private String Approval;

    public String getId()
    {
        return Id;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public String getAddress()
    {
        return Address;
    }

    public void setAddress(String Address)
    {
        this.Address = Address;
    }

    public String getCity()
    {
        return City;
    }

    public void setCity(String City)
    {
        this.City = City;
    }

    public String getProvince()
    {
        return Province;
    }

    public void setProvince(String Province)
    {
        this.Province = Province;
    }

    public String getStatus()
    {
        return Status;
    }

    public void setStatus(String Status)
    {
        this.Status = Status;
    }

    public String getApproval()
    {
        return Approval;
    }

    public void setApproval(String Approval)
    {
        this.Approval = Approval;
    }

    public String getLink()
    {
        return Link;
    }

    public void setLink(String Link)
    {
        this.Link = Link;
    }

    public String getPhone()
    {
        return Phone;
    }

    public void setPhone(String Phone)
    {
        this.Phone = Phone;
    }

}
