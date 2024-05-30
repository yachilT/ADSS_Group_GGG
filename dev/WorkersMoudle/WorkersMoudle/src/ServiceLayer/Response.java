package ServiceLayer;

public class Response
{
    public String ErrorMessage;
    public Object ReturnValue;


    public Response()
    {
        this.ReturnValue = null;
        this.ErrorMessage = null;
    }

    public Response(String msg)
    {
        this.ErrorMessage = msg;
        this.ReturnValue = null;
    }

    public Response(Object value)
    {
        this.ReturnValue = value;
        this.ErrorMessage = null;
    }

    public Response(Object value, String msg)
    {
        this.ReturnValue = value;
        this.ErrorMessage = msg;
    }
    public boolean ErrorOccured()
    {
        return ErrorMessage != null;
    }

    public String GetErrorMessage()
    {
        return ErrorMessage;
    }

    public Object GetReturnValue()
    {
        return ReturnValue;
    }

    public void SetReturnValue(Object value)
    {
        ReturnValue = value;
    }

    public void SetErrorMessage(String msg)
    {
        ErrorMessage = msg;
    }
}
