package net.yazeed44.cdpnebot;

public class Introduction {
	
	
	public final String city;
	public final int userId;
	public String hobbies;
	public String preferedTraitsInRoommate;
	public String unpreferedTraitsInRoommate;

	public Introduction(final String city,final int userId) {
		super();
		this.city = city;
		this.userId = userId;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public void setPreferedTraitsInRoommate(String prefereredTraitsInRoommate) {
		this.preferedTraitsInRoommate = prefereredTraitsInRoommate;
	}

	public void setUnpreferedTraitsInRoommate(String unpreferedTraitsInRoommate) {
		this.unpreferedTraitsInRoommate = unpreferedTraitsInRoommate;
	}
	
	
	
	

}
