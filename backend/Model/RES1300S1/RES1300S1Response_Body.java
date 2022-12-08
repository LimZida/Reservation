package model.RES1300S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1300S1Response_Body {
	/**
	* 푸시 예약 리스트
	*/
	private List<RES1300S1Response_Body_trxIdList> trxIdList = new ArrayList<RES1300S1Response_Body_trxIdList>();
	/**
	* 예약 상세 존재 여부(true : 존재 / false : 미존재)
	*/
	private boolean Flag = false;
	/**
	* 예약 종료 여부
	*/
	private String reserveEndCheck = "";
	/**
	* 회원 일련번호(예약자)
	*/
	private Integer userSeqPK = null;
	/**
	* 30분 뒤 연장 가능 여부
	*/
	private boolean checkAvailable = false;
	/**
	* 예약 장소
	*/
	private String reserveRoom = "";
	/**
	* 예약명
	*/
	private String reserveName = "";
	/**
	* 예약 시작 시간
	*/
	private String reserveStart = "";
	/**
	* 예약 종료 시간
	*/
	private String reserveEnd = "";
	/**
	* 예약 날짜
	*/
	private String reserveDate = "";
	/**
	* 예약자 이름
	*/
	private String userName = "";
	/**
	* 참석자 리스트 [홍길동, 홍길동1, 홍길동2, ...]
	*/
	private List<RES1300S1Response_Body_attendList> attendList = new ArrayList<RES1300S1Response_Body_attendList>();

	public RES1300S1Response_Body() {
	}

	public RES1300S1Response_Body(JsonNode jsonNode) {
		this.trxIdList = new ArrayList<RES1300S1Response_Body_trxIdList>();
		JsonNode trxIdListNode = jsonNode.path("trxIdList");
		for (Iterator<JsonNode> iter = trxIdListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			trxIdList.add(new RES1300S1Response_Body_trxIdList(node));
		}
		this.Flag = jsonNode.path("Flag").booleanValue();
		this.reserveEndCheck = jsonNode.path("reserveEndCheck").textValue();
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
		this.checkAvailable = jsonNode.path("checkAvailable").booleanValue();
		this.reserveRoom = jsonNode.path("reserveRoom").textValue();
		this.reserveName = jsonNode.path("reserveName").textValue();
		this.reserveStart = jsonNode.path("reserveStart").textValue();
		this.reserveEnd = jsonNode.path("reserveEnd").textValue();
		this.reserveDate = jsonNode.path("reserveDate").textValue();
		this.userName = jsonNode.path("userName").textValue();
		this.attendList = new ArrayList<RES1300S1Response_Body_attendList>();
		JsonNode attendListNode = jsonNode.path("attendList");
		for (Iterator<JsonNode> iter = attendListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			attendList.add(new RES1300S1Response_Body_attendList(node));
		}
	}

	@JsonProperty("trxIdList")
	public List<RES1300S1Response_Body_trxIdList> getTrxIdList() {
		return this.trxIdList;
	}

	public void setTrxIdList(List<RES1300S1Response_Body_trxIdList> trxIdList) {
		this.trxIdList = trxIdList;
	}

	@JsonProperty("Flag")
	public boolean getFlag() {
		return this.Flag;
	}

	public void setFlag(boolean Flag) {
		this.Flag = Flag;
	}

	@JsonProperty("reserveEndCheck")
	public String getReserveEndCheck() {
		return this.reserveEndCheck;
	}

	public void setReserveEndCheck(String reserveEndCheck) {
		this.reserveEndCheck = reserveEndCheck;
	}

	@JsonProperty("userSeqPK")
	public Integer getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(Integer userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	@JsonProperty("checkAvailable")
	public boolean getCheckAvailable() {
		return this.checkAvailable;
	}

	public void setCheckAvailable(boolean checkAvailable) {
		this.checkAvailable = checkAvailable;
	}

	@JsonProperty("reserveRoom")
	public String getReserveRoom() {
		return this.reserveRoom;
	}

	public void setReserveRoom(String reserveRoom) {
		this.reserveRoom = reserveRoom;
	}

	@JsonProperty("reserveName")
	public String getReserveName() {
		return this.reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	@JsonProperty("reserveStart")
	public String getReserveStart() {
		return this.reserveStart;
	}

	public void setReserveStart(String reserveStart) {
		this.reserveStart = reserveStart;
	}

	@JsonProperty("reserveEnd")
	public String getReserveEnd() {
		return this.reserveEnd;
	}

	public void setReserveEnd(String reserveEnd) {
		this.reserveEnd = reserveEnd;
	}

	@JsonProperty("reserveDate")
	public String getReserveDate() {
		return this.reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	@JsonProperty("userName")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("attendList")
	public List<RES1300S1Response_Body_attendList> getAttendList() {
		return this.attendList;
	}

	public void setAttendList(List<RES1300S1Response_Body_attendList> attendList) {
		this.attendList = attendList;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1300S1Response_Body [");
		builder.append("trxIdList=").append(trxIdList);
		builder.append(", ");
		builder.append("Flag=").append(Flag);
		builder.append(", ");
		builder.append("reserveEndCheck=").append(reserveEndCheck);
		builder.append(", ");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append(", ");
		builder.append("checkAvailable=").append(checkAvailable);
		builder.append(", ");
		builder.append("reserveRoom=").append(reserveRoom);
		builder.append(", ");
		builder.append("reserveName=").append(reserveName);
		builder.append(", ");
		builder.append("reserveStart=").append(reserveStart);
		builder.append(", ");
		builder.append("reserveEnd=").append(reserveEnd);
		builder.append(", ");
		builder.append("reserveDate=").append(reserveDate);
		builder.append(", ");
		builder.append("userName=").append(userName);
		builder.append(", ");
		builder.append("attendList=").append(attendList);
		builder.append("]");
		return builder.toString();
	}
}