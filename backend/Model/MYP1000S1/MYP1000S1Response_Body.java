package model.MYP1000S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class MYP1000S1Response_Body {
	/**
	* 사용자 직급
	*/
	private String userPosition = "";
	/**
	* 사용자 전화번호(01012345678)
	*/
	private String userPhone = "";
	/**
	* 예약정보
	*/
	private List<MYP1000S1Response_Body_reserveList> reserveList = new ArrayList<MYP1000S1Response_Body_reserveList>();
	/**
	* 사용자 이름
	*/
	private String userName = "";
	/**
	* 사용자 메일
	*/
	private String userEmail = "";
	/**
	* 사용자 부서
	*/
	private String userDep = "";

	public MYP1000S1Response_Body() {
	}

	public MYP1000S1Response_Body(JsonNode jsonNode) {
		this.userPosition = jsonNode.path("userPosition").textValue();
		this.userPhone = jsonNode.path("userPhone").textValue();
		this.reserveList = new ArrayList<MYP1000S1Response_Body_reserveList>();
		JsonNode reserveListNode = jsonNode.path("reserveList");
		for (Iterator<JsonNode> iter = reserveListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			reserveList.add(new MYP1000S1Response_Body_reserveList(node));
		}
		this.userName = jsonNode.path("userName").textValue();
		this.userEmail = jsonNode.path("userEmail").textValue();
		this.userDep = jsonNode.path("userDep").textValue();
	}

	@JsonProperty("userPosition")
	public String getUserPosition() {
		return this.userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	@JsonProperty("userPhone")
	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@JsonProperty("reserveList")
	public List<MYP1000S1Response_Body_reserveList> getReserveList() {
		return this.reserveList;
	}

	public void setReserveList(List<MYP1000S1Response_Body_reserveList> reserveList) {
		this.reserveList = reserveList;
	}

	@JsonProperty("userName")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("userEmail")
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@JsonProperty("userDep")
	public String getUserDep() {
		return this.userDep;
	}

	public void setUserDep(String userDep) {
		this.userDep = userDep;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MYP1000S1Response_Body [");
		builder.append("userPosition=").append(userPosition);
		builder.append(", ");
		builder.append("userPhone=").append(userPhone);
		builder.append(", ");
		builder.append("reserveList=").append(reserveList);
		builder.append(", ");
		builder.append("userName=").append(userName);
		builder.append(", ");
		builder.append("userEmail=").append(userEmail);
		builder.append(", ");
		builder.append("userDep=").append(userDep);
		builder.append("]");
		return builder.toString();
	}
}