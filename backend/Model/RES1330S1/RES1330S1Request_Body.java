package model.RES1330S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1330S1Request_Body {
	/**
	* 참석자 일련번호 ex) [12,55,35]
	*/
	private List<Integer> attendUserSeqList = new ArrayList<Integer>();
	/**
	* 푸시 예약 날짜 (YYYYMMDD)
	*/
	private String trxDate = "";
	/**
	* 푸시 예약 일련번호 ex) [431,134,55]
	*/
	private List<Integer> trxId = new ArrayList<Integer>();
	/**
	* 예약 일련번호
	*/
	private Integer reserveSeqPK = null;
	/**
	* 회원 일련번호
	*/
	private Integer userSeqPK = null;
	/**
	* 예약명
	*/
	private String reserveName = "";

	public RES1330S1Request_Body() {
	}

	public RES1330S1Request_Body(JsonNode jsonNode) {
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
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
		this.reserveName = jsonNode.path("reserveName").textValue();
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

	@JsonProperty("reserveSeqPK")
	public Integer getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(Integer reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
	}

	@JsonProperty("userSeqPK")
	public Integer getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(Integer userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	@JsonProperty("reserveName")
	public String getReserveName() {
		return this.reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1330S1Request_Body [");
		builder.append("attendUserSeqList=").append(attendUserSeqList);
		builder.append(", ");
		builder.append("trxDate=").append(trxDate);
		builder.append(", ");
		builder.append("trxId=").append(trxId);
		builder.append(", ");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append(", ");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append(", ");
		builder.append("reserveName=").append(reserveName);
		builder.append("]");
		return builder.toString();
	}
}