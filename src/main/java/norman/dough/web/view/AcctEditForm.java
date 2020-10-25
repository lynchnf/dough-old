package norman.dough.web.view;

import norman.dough.domain.Acct;
import norman.dough.exception.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class AcctEditForm {
    private Long id;
    private Integer version = 0;
    @NotBlank(message = "Name may not be blank.")
    @Size(max = 50, message = "Name may not be over {max} characters long.")
    private String name;
    @Size(max = 50, message = "Nickname may not be over {max} characters long.")
    private String nickname;
    @NotBlank(message = "Type may not be blank.")
    @Size(max = 10, message = "Type may not be over {max} characters long.")
    private String type;
    @Size(max = 50, message = "Address Name may not be over {max} characters long.")
    private String addressName;
    @Size(max = 50, message = "Address Line 1 may not be over {max} characters long.")
    private String address1;
    @Size(max = 50, message = "Address Line 2 may not be over {max} characters long.")
    private String address2;
    @Size(max = 50, message = "City may not be over {max} characters long.")
    private String city;
    @Size(max = 2, message = "State Code may not be over {max} characters long.")
    private String state;
    @Size(max = 10, message = "Zip Code may not be over {max} characters long.")
    private String zipCode;
    @Size(max = 20, message = "Phone Number may not be over {max} characters long.")
    private String phoneNumber;
    @Digits(integer = 9, fraction = 2, message = "Credit Limit value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal creditLimit;
    @NotNull(message = "Active may not be blank.")
    private Boolean active;

    public AcctEditForm() {
    }

    public AcctEditForm(Acct entity) {
        id = entity.getId();
        version = entity.getVersion();
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
        active = entity.getActive();
    }

    public Acct toEntity() throws NotFoundException {
        Acct entity = new Acct();
        entity.setId(id);
        entity.setVersion(version);
        entity.setName(StringUtils.trimToNull(name));
        entity.setNickname(StringUtils.trimToNull(nickname));
        entity.setType(StringUtils.trimToNull(type));
        entity.setAddressName(StringUtils.trimToNull(addressName));
        entity.setAddress1(StringUtils.trimToNull(address1));
        entity.setAddress2(StringUtils.trimToNull(address2));
        entity.setCity(StringUtils.trimToNull(city));
        entity.setState(StringUtils.trimToNull(state));
        entity.setZipCode(StringUtils.trimToNull(zipCode));
        entity.setPhoneNumber(StringUtils.trimToNull(phoneNumber));
        entity.setCreditLimit(creditLimit);
        entity.setActive(active);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
