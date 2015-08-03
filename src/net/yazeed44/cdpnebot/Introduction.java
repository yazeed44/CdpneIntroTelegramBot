package net.yazeed44.cdpnebot;

public class Introduction {
	
	
	public final String city;
	public final int userId;
	public String username;
	public String hobbies;
	public String preferedTraitsInRoommate;
	public String unpreferedTraitsInRoommate;

	public Introduction(final int userId,final String city) {
		super();
		this.city = city;
		this.userId = userId;
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
	
	

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public void setPreferedTraitsInRoommate(String prefereredTraitsInRoommate) {
		this.preferedTraitsInRoommate = prefereredTraitsInRoommate;
	}

	public void setUnpreferedTraitsInRoommate(String unpreferedTraitsInRoommate) {
		this.unpreferedTraitsInRoommate = unpreferedTraitsInRoommate;
	}
	
	public void setUsername(final String username){
		this.username = username;
	}

	
	public String generateIntroText(){
		final StringBuilder builder = new StringBuilder();
		
		
		builder.append("@"+username)
		.append("\n -")
		.append("المدينة : ")
		.append(city)
		.append("\n -")
		.append("الهوايات : ")
		.append(hobbies)
		.append("\n -")
		.append("الصفات المرغوبة في الروم ميت : ")
		.append(preferedTraitsInRoommate)
		.append("\n -")
		.append("الصفات الغير محببة في الروم ميت : ")
		.append(unpreferedTraitsInRoommate);
		
		
		
		return builder.toString();
	}
	
	
	

}
