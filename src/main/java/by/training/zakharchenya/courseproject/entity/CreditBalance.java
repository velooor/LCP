package by.training.zakharchenya.courseproject.entity;

import org.json.JSONException;
import org.json.JSONObject;

/** Entity class, serves for processing relative object CreditBalance from database.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class CreditBalance {
    private int moneyAmount;
    private int dept;

    public CreditBalance(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public CreditBalance(int moneyAmount, int dept) {
        this.moneyAmount = moneyAmount;
        this.dept = dept;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try{

            json.put("moneyAmount", moneyAmount);
        } catch(JSONException e){
            return null;
        }

        return json;
    }

    public String toXml() {
        StringBuffer xml = new StringBuffer();
        xml.append("<CreditBalance>");
        xml.append("<moneyAmount>"+moneyAmount+"</moneyAmount>");

        xml.append("</CreditBalance>");
        return xml.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditBalance that = (CreditBalance) o;

        if (moneyAmount != that.moneyAmount) return false;
        return dept == that.dept;
    }

    @Override
    public int hashCode() {
        int result = moneyAmount;
        result = 31 * result + dept;
        return result;
    }
}
