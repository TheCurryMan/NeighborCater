package hackthenorth.neighborcater.models;

/**
 * Created by rowandempster on 9/17/16.
 */

public class XEResponse {
    private XETo[] to;

    private String timestamp;

    private String amount;

    private String terms;

    private String privacy;

    private String from;

    public XETo[] getTo ()
    {
        return to;
    }

    public void setTo (XETo[] to)
    {
        this.to = to;
    }

    public String getTimestamp ()
    {
        return timestamp;
    }

    public void setTimestamp (String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getTerms ()
    {
        return terms;
    }

    public void setTerms (String terms)
    {
        this.terms = terms;
    }

    public String getPrivacy ()
    {
        return privacy;
    }

    public void setPrivacy (String privacy)
    {
        this.privacy = privacy;
    }

    public String getFrom ()
    {
        return from;
    }

    public void setFrom (String from)
    {
        this.from = from;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [to = "+to+", timestamp = "+timestamp+", amount = "+amount+", terms = "+terms+", privacy = "+privacy+", from = "+from+"]";
    }
}
