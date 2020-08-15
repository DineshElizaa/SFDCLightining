package com.infy.autoqa.karate.DataClass;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class AccountsData {
	
	public Object _id;
	public String role;
	public String testCaseID;
	
	@Getter
	@Setter
	public class AccountsDetails{
		public String environment;
		public String accountName;
	}
	
}
