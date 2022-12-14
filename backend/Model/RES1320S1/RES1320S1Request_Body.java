package model.RES1320S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1320S1Request_Body {
	/**
	* 종료 시간(HH:MM) ex) 09:15
	*/
	private String Hour = "";
	/**
	* 추가-참석자 일련번호 ex) [12,55,35]
	*/
	private List<Integer> attendUserSeqList = new ArrayList<Integer>();
	/**
	* 추가-푸시 예약 날짜 (YYYYMMDD)
	*/
	private String trxDate = "";
	/**
	* 추가-푸시 예약 일련번호 ex) [431,134]
	*/
	private List<Integer> trxId = new ArrayList<Integer>();
	/**
	* 추가-에약명
	*/
	private String reserveName = "";
	/**
	* 예약 일련번호
	*/
	private int reserveSeqPK = 0;
	/**
	* 회원 일련번호
	*/
	private int userSeqPK = 0;

	public RES1320S1Request_Body() {
	}

	public RES1320S1Request_Body(JsonNode jsonNode) {
		this.Hour = jsonNode.path("Hour").textValue();
		this.attendUserSeqList = new ArrayList<Integer>();
		JsonNode attendUserSeqListNode = jsonNode.path("attendUserSeqList");
		for (Iterator<JsonNode> iter = attendUserSeqListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			attendUserSeqList.add(node.intValue());
		}
		this.trxDate = jsonNode.path("trxDate").textValue();
		this.trxId = new ArrayList<Integer>();
		JsonNode trxIdNode = jsonNode.path("trxId");
		for (Iterator<JsonNode> iter = trxIdNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			trxId.add(node.intValue());
		}
		this.reserveName = jsonNode.path("reserveName").textValue();
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
	}

	@JsonProperty("Hour")
	public String getHour() {
		return this.Hour;
	}

	public void setHour(String Hour) {
		this.Hour = Hour;
	}

	@JsonProperty("attendUserSeqList")
	public List<Integer> getAttendUserSeqList() {
		return this.attendUserSeqList;
	}

	public void setAttendUserSeqList(List<Integer> attendUserSeqList) {
		this.attendUserSeqList = attendUserSeqList;
	}

	@JsonProperty("trxDate")
	public String getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	@JsonProperty("trxId")
	public List<Integer> getTrxId() {
		return this.trxId;
	}

	public void setTrxId(List<Integer> trxId) {
		this.trxId = trxId;
	}

	@JsonProperty("reserveName")
	public String getReserveName() {
		return this.reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	@JsonProperty("reserveSeqPK")
	public int getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(int reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
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
		builder.append("RES1320S1Request_Body [");
		builder.append("Hour=").append(Hour);
		builder.append(", ");
		builder.append("attendUserSeqList=").append(attendUserSeqList);
		builder.append(", ");
		builder.append("trxDate=").append(trxDate);
		builder.append(", ");
		builder.append("trxId=").append(trxId);
		builder.append(", ");
		builder.append("reserveName=").append(reserveName);
		builder.append(", ");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append(", ");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append("]");
		return builder.toString();
	}
}