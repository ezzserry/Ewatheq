package ae.ewatheq.models;

public class FloorPlanArea {
	private String strId;

	private int intId;
	
	private String name;

	private String coords;
	private String exhId;
	private String shape;
	
	
	
	public FloorPlanArea() {
		// TODO Auto-generated constructor stub
		intId = 0;
		strId = "";
		shape = "";
		name = "";

		coords = "";
		exhId = "";
		
	}
	public FloorPlanArea(String strId,int intId,String name,String coords, String exhId, String shape ) {
		// TODO Auto-generated constructor stub
		this.coords = coords;

		this.strId = strId;

		this.intId = intId;
		this.shape = shape;
		this.name = name;
		this.exhId = exhId;
	}
	public FloorPlanArea(String strId,String name,String coords, String exhId ) {
		// TODO Auto-generated constructor stub
		this.coords = coords;

		this.strId = strId;

		this.intId = 0;
		this.shape = "";
		this.name = name;
		this.exhId = exhId;
	}
	public FloorPlanArea(String strId,String name,String coords, String exhId,String shape ) {
		// TODO Auto-generated constructor stub
		this.coords = coords;

		this.strId = strId;

		this.intId = 0;
		this.shape = shape;
		this.name = name;
		this.exhId = exhId;
	}
	
	
	public String getStrId()
	{
		return strId;
	}
	public int getIntId()
	{
		
		
		return intId;
	}
	public String getShape()
	{
		return shape;
	}
	public String getName()
	{
		if (this.name == null)
			return "";
		return name;
	}
	public String getCoords()
	{
		return this.coords;
	}
	public String getExhId()
	{
		if (this.exhId == null)
			return "";
		return exhId;
	}
	
	
	
	
	
	public void setStrId(String var)
	{
		 strId = var;
	}
	public void setintId(int var)
	{
		 intId = var;
	}
	public void setShape(String var)
	{
		 shape = var;
	}
	public void setName(String var)
	{
		 name = var;
	}
	public void setCoords(String var)
	{
		 coords = var;
	}
	public void setExhId(String var)
	{
		 exhId = var;
	}
}
