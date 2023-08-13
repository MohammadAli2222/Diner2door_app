package android.example.vendorpage;
public class model
{
    String item_name,purl,price;
    String outlet_purl,OutletName;
    String PhoneNumber,total_amount,transactionID;
    String Email,Password,UID,Username;
    String cart_item_name,cart_item_price,cart_item_purl,cart_item_quantity;
    String Amount,TransactionID;

    model()
    {

    }
    public model(String item_name, String price, String purl,String OutletName,String Phonenumber,String total_amount,
                 String transactionID,String Username, String Email,String Password, String outlet_purl,
                 String cart_item_name,String cart_item_price,String cart_item_purl,String cart_item_quantity,
                 String UID,String amount,String TransactionID)
    {
        this.item_name = item_name;
        this.price = price;
        this.purl = purl;
        this.OutletName=OutletName;
        this.total_amount=total_amount;
        this.transactionID=transactionID;
        this.Email=Email;
        this.Username=Username;
        this.PhoneNumber=Phonenumber;
        this.Password=Password;
        this.outlet_purl=outlet_purl;
        this.cart_item_name=cart_item_name;
        this.cart_item_price=cart_item_price;
        this.cart_item_purl=cart_item_purl;
        this.cart_item_quantity=cart_item_quantity;
        this.UID=UID;
        this.Amount=amount;
        this.transactionID=TransactionID;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getprice() {
        return price;
    }

    public String getPurl() {
        return purl;
    }

    public String getOutletName() {
        return OutletName;
    }

    public String getCustomer_phonenumber() {
        return PhoneNumber;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getCustomer_fullname() {return Username;}

    public String getCustomer_email() {
        return Email;
    }

    public String getOutlet_purl(){ return outlet_purl;}

    public String getCart_item_name() {
        return cart_item_name;
    }
    public String getCart_item_price() {
        return cart_item_price;
    }
    public String getCart_item_purl() {
        return cart_item_purl;
    }
    public String getCart_item_quantity()
    {
        return cart_item_quantity;
    }


}
