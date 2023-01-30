package example.spring.core.bean.entity.person;

import example.spring.core.bean.annotation.Super;

/**
 * 超级用户
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
@Super
public class SuperUser extends User {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
            "address='" + address + '\'' +
            "} " + super.toString();
    }

}
