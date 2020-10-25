package norman.dough.web.view;

import norman.dough.domain.Acct;

import java.math.BigDecimal;

public class AcctView {
    private Long id;
    private String name;
    private String nickname;
    private String type;
    private String addressName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private BigDecimal creditLimit;
    private String cronString;
    private Boolean active;

    public AcctView(Acct entity) {
        id = entity.getId();
        name = entity.getName();
        nickname = entity.getNickname();
        type = entity.getType();
        addressName = entity.getAddressName();
        address1 = entity.getAddress1();
        address2 = entity.getAddress2();
        city = entity.getCity();
        state = entity.getState();
        zipCode = entity.getZipCode();
        phoneNumber = entity.getPhoneNumber();
        creditLimit = entity.getCreditLimit();
        cronString = entity.getCronString();
        active = entity.getActive();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getType() {
        return type;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public String getCronString() {
        return cronString;
    }

    public Boolean getActive() {
        return active;
    }
}
