package org.mdcconcepts.androidapp.mdcspauserapp.findspa;

public class Spa_Data {
	public String Spa_Name = "";
	public String Spa_Id = "";
	public String Spa_Lat = "";
	public String Spa_Long = "";
	public String Spa_Address = "";

	public Spa_Data(String Spa_Name,String Spa_Id,String Spa_Lat,String Spa_Long,String Spa_Address)
	{
		// TODO Auto-generated constructor stub
		this.Spa_Id=Spa_Id.toString();
		this.Spa_Name=Spa_Name.toString();
		this.Spa_Lat=Spa_Lat.toString();
		this.Spa_Long=Spa_Long.toString();
		this.Spa_Address=Spa_Address.toString();
	}
}
