package model.RES1100S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1100S1Request_Body {
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
	* 예약명
	*/
	private String reserveName = "";
	/**
	* 회원 일련번호 List(참석자들의 회원 일련번호) ex) [20,1,35,22,4]
	*/
	private List<Integer> attendUserSeqList = new ArrayList<Integer>();
	/**
	* 회원 일련번호(예약자의 회원 일련번호)
	*/
	private int userSeqPK = 0;

	public RES1100S1Request_Body() {
	}

	public RES1100S1Request_Body(JsonNode jsonNode) {
		this.reserveRoom = jsonNode.path("reserveRoom").textValue();
		this.reserveDate = jsonNode.path("reserveDate").textValue();
		this.reserveStart = jsonNode.path("reserveStart").textValue();
		this.reserveEnd = jsonNode.path("reserveEnd").textValue();
		this.reserveName = jsonNode.path("reserveName").textValue();
		this.attendUserSeqList = new ArrayList<Integer>();
		JsonNode attendUserSeqListNode = jsonNode.path("attendUserSeqList");
		for (Iterator<JsonNode> iter = attendUserSeqListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			attendUserSeqList.add(node.intValue());
		}
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
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

	@JsonProperty("attendUserSeqList")
	public List<Integer> getAttendUserSeqList() {
		return this.attendUserSeqList;
	}

	public void setAttendUserSeqList(List<Integer> attendUserSeqList) {
		this.attendUserSeqList = attendUserSeqList;
	}

	@JsonProperty("userSeqPK")
	public int getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(int userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1100S1Request_Body [");
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
		builder.append("attendUserSeqList=").append(attendUserSeqList);
		builder.append(", ");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append("]");
		return builder.toString();
	}
}