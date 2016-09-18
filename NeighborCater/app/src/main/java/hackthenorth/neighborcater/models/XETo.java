package hackthenorth.neighborcater.models;

/**
 * Created by rowandempster on 9/17/16.
 */

public class XETo {
    private String quotecurrency;

    private String mid;

    public String getQuotecurrency ()
    {
        return quotecurrency;
    }

    public void setQuotecurrency (String quotecurrency)
    {
        this.quotecurrency = quotecurrency;
    }

    public String getMid ()
    {
        return mid;
    }

    public void setMid (String mid)
    {
        this.mid = mid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [quotecurrency = "+quotecurrency+", mid = "+mid+"]";
    }
}
