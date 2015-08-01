package net.yazeed44.cdpnebot;

public class Introduction {
	
	
	public final String city;
	public final int userId;
	public String hobbies;
	public String preferedTraitsInRoommate;
	public String unpreferedTraitsInRoommate;

	public Introduction(final int userId,final String city) {
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

	@Override
	public String toString() {
		return "Introduction [city=" + city + ", userId=" + userId
				+ ", hobbies=" + hobbies + ", preferedTraitsInRoommate="
				+ preferedTraitsInRoommate + ", unpreferedTraitsInRoommate="
				+ unpreferedTraitsInRoommate + "]";
	}

	@Override
	public boolean equals(final Object obj){
		return obj instanceof Introduction && ((Introduction)obj).userId == userId;
	}
	
	
	
	

}
