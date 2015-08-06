package net.yazeed44.cdpnebot;

public class Introduction {
	
	
	private final String mCity;
	private final int mUserId;
	private String mUsername;
	private String mHobbies;
	private String mPreferedTraitsInRoommate;
	private String mUnpreferedTraitsInRoommate;

	public Introduction(final int userId,final String city) {
		super();
		this.mCity = city;
		this.mUserId = userId;
	}
	
	@Override
	public String toString() {
		return "Introduction [city=" + getCity() + ", userId=" + getUserId()
				+ ", hobbies=" + getHobbies() + ", preferedTraitsInRoommate="
				+ getPreferedTraitsInRoommate() + ", unpreferedTraitsInRoommate="
				+ getUnpreferedTraitsInRoommate() + "]";
	}
	
	

	@Override
	public boolean equals(final Object obj){
		return obj instanceof Introduction && ((Introduction)obj).getUserId() == getUserId();
	}
	
	

	public int getUserId() {
		return mUserId;
	}

	public String getCity() {
		return mCity;
	}

	public void setHobbies(String hobbies) {
		this.mHobbies = hobbies;
	}

	public String getHobbies() {
		return mHobbies;
	}

	public void setPreferedTraitsInRoommate(String prefereredTraitsInRoommate) {
		this.mPreferedTraitsInRoommate = prefereredTraitsInRoommate;
	}

	public String getPreferedTraitsInRoommate() {
		return mPreferedTraitsInRoommate;
	}

	public void setUnpreferedTraitsInRoommate(String unpreferedTraitsInRoommate) {
		mUnpreferedTraitsInRoommate = unpreferedTraitsInRoommate;
	}
	
	public String getUnpreferedTraitsInRoommate() {
		return mUnpreferedTraitsInRoommate;
	}

	public void setUsername(final String username){
		this.mUsername = username;
	}

	
	public String getUsername() {
		return mUsername;
	}

	public String generateIntroText(){
		final StringBuilder builder = new StringBuilder();
		
		
		builder.append("@"+getUsername())
		.append("\n -")
		.append("المدينة : ")
		.append(getCity())
		.append("\n -")
		.append("الهوايات : ")
		.append(getHobbies())
		.append("\n -")
		.append("الصفات المرغوبة في الروم ميت : ")
		.append(getPreferedTraitsInRoommate())
		.append("\n -")
		.append("الصفات الغير محببة في الروم ميت : ")
		.append(getUnpreferedTraitsInRoommate());
		
		
		
		return builder.toString();
	}
	
	
	

}
