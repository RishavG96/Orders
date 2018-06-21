package hera.com.orders.service;

/**
 * Created by rishavg on 6/18/17.
 */

public class Sorting_Partner {
    public String id, code, name, address, city, amount, type, discount, status, businessHours, timeOfReceipt, responsiblePerson, forMobile;
    public Sorting_Partner(String id,String code,String name,String address,String city,String amount,
                           String type,String discount,String status,String businessHours,String timeOfReceipt,
                           String responsiblePerson,String forMobile)
    {
        this.id=id;
        this.code=code;
        this.name=name;
        this.address=address;
        this.city=city;
        this.amount=amount;
        this.type=type;
        this.discount=discount;
        this.status=status;
        this.businessHours=businessHours;
        this.timeOfReceipt=timeOfReceipt;
        this.responsiblePerson=responsiblePerson;
        this.forMobile=forMobile;
    }
}
