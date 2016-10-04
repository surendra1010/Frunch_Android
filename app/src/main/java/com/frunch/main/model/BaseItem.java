package com.frunch.main.model;

public class BaseItem {
	public String image;
	public String title;
	public String description;
	public String price;
	public int 	num;
	public BaseItem(String s, String t, String d,String p)
	{
		image = s;
		title = t;
		description = d;
		price = p;
		num = 1;
	}
}
