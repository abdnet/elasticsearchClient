package imnet.ft.commun.util;

public enum MatchVariableName
{
	
	CLIENTTRANSPORTSNIFF("client.transport.sniff");
    
    private String text;
    
    public String getText() {
		return text;
	}

	private MatchVariableName(String text)
    {
        this.text = text;
    }

}
