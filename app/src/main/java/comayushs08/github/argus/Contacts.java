package comayushs08.github.argus;

/**
 * Created by ayush on 16/11/17.
 */

public class Contacts {

    private String mContactName, mContactPhone;

    public Contacts (String contactName, String contactPhone) {
        this.mContactName = contactName;
        this.mContactPhone = contactPhone;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public String getContactPhone() {
        return mContactPhone;
    }

    public void setContactPhone(String mContactPhone) {
        this.mContactPhone = mContactPhone;
    }
}
