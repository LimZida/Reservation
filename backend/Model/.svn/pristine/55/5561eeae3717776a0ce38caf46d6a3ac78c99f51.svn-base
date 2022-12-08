package model.RES1200S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1200S1Request_Body {
	/**
	* 회원 일련번호(예약자)
	*/
	private Integer userSeqPK = null;
	/**
	* 변경 전 회원 일련번호 List(참석자들의 회원 일련번호) ex) [1,2,3,4]
	*/
	private List<Integer> pastAttendUserSeqList = new ArrayList<Integer>();
	/**
	* 푸시 예약 날짜 (YYYYMMDD)
	*/
	private String trxDate = "";
	/**
	* 예약 일련번호
	*/
	private Integer reserveSeqPK = null;
	/**
	* 푸시 예약 일련번호 ex) [431,134,55]
	*/
	private List<Integer> trxId = new ArrayList<Integer>();
	/**
	* 예약 장소
	*/
	private String reserveRoom = "";
	/**
	* 예약 날짜
	*/
	private String reserveDate = "";
	/**
	* 예약 시작 시간
	*/
	private String reserveStart = "";
	/**
	* 예약 종료 시간
	*/
	private String reserveEnd = "";
	/**
	* 변경 후 예약명
	*/
	private String reserveName = "";
	/**
	* 변경 전 예약명
	*/
	private String pastReserveName = "";
	/**
	* 변경된 회원 일련번호 List(참석자들의 회원 일련번호) ex)[25,31,1,2,3]
	*/
	private List<Integer> attendUserSeqList = new ArrayList<Integer>();

	public RES1200S1Request_Body() {
	}

	public RES1200S1Request_Body(JsonNode jsonNode) {
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
		this.pastAttendUserSeqList = new ArrayList<Integer>();
		JsonNode pastAttendUserSeqListNode = jsonNode.path("pastAttendUserSeqList");
		for (Iterator<JsonNode> iter = pastAttendUserSeqListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			pastAttendUserSeqList.add(node.intValue());
		}
		this.trxDate = jsonNode.path("trxDate").textValue();
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
		this.trxId = new ArrayList<Integer>();
		JsonNode trxIdNode = jsonNode.path("trxId");
		for (Iterator<JsonNode> iter = trxIdNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			trxId.add(node.intValue());
		}
		this.reserveRoom = jsonNode.path("reserveRoom").textValue();
		this.reserveDate = jsonNode.path("reserveDate").textValue();
		this.reserveStart = jsonNode.path("reserveStart").textValue();
		this.reserveEnd = jsonNode.path("reserveEnd").textValue();
		this.reserveName = jsonNode.path("reserveName").textValue();
		this.pastReserveName = jsonNode.path("pastReserveName").textValue();
		this.attendUserSeqList = new ArrayList<Integer>();
		JsonNode attendUserSeqListNode = jsonNode.path("attendUserSeqList");
		for (Iterator<JsonNode> iter = attendUserSeqListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			attendUserSeqList.add(node.intValue());
		}
	}

	@JsonProperty("userSeqPK")
	public Integer getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(Integer userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	@JsonProperty("pastAttendUserSeqList")
	public List<Integer> getPastAttendUserSeqList() {
		return this.pastAttendUserSeqList;
	}

	public void setPastAttendUserSeqList(List<Integer> pastAttendUserSeqList) {
		this.pastAttendUserSeqList = pastAttendUserSeqList;
	}

	@JsonProperty("trxDate")
	public String getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	@JsonProperty("reserveSeqPK")
	public Integer getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(Integer reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
	}

	@JsonProperty("trxId")
	public List<Integer> getTrxId() {
		return this.trxId;
	}

	public void setTrxId(List<Integer> trxId) {
		this.trxId = trxId;
	}

	@JsonProperty("reserveRoom")
	public String getReserveRoom() {
		return this.reserveRoom;
	}

	public void setReserveRoom(String reserveRoom) {
		this.reserveRoom = reserveRoom;
	}

	@JsonProperty("reserveDate")
	public String getReserveDate() {
		return this.reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
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

	@JsonProperty("reserveName")
	public String getReserveName() {
		return this.reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	@JsonProperty("pastReserveName")
	public String getPastReserveName() {
		return this.pastReserveName;
	}

	public void setPastReserveName(String pastReserveName) {
		this.pastReserveName = pastReserveName;
	}

	@JsonProperty("attendUserSeqList")
	public List<Integer> getAttendUserSeqList() {
		return this.attendUserSeqList;
	}

	public void setAttendUserSeqList(List<Integer> attendUserSeqList) {
		this.attendUserSeqList = attendUserSeqList;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1200S1Request_Body [");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append(", ");
		builder.append("pastAttendUserSeqList=").append(pastAttendUserSeqList);
		builder.append(", ");
		builder.append("trxDate=").append(trxDate);
		builder.append(", ");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append(", ");
		builder.append("trxId=").append(trxId);
		builder.append(", ");
		builder.append("reserveRoom=").append(reserveRoom);
		builder.append(", ");
		builder.append("reserveDate=").append(reserveDate);
		builder.append(", ");
		builder.append("reserveStart=").append(reserveStart);
		builder.append(", ");
		builder.append("reserveEnd=").append(reserveEnd);
		builder.append(", ");
		builder.append("reserveName=").append(reserveName);
		builder.append(", ");
		builder.append("pastReserveName=").append(pastReserveName);
		builder.append(", ");
		builder.append("attendUserSeqList=").append(attendUserSeqList);
		builder.append("]");
		return builder.toString();
	}
}