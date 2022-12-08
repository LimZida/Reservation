package model.RES1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 예약 내역 리스트
 */
public class RES1000S1Response_Body_reserveList {
	/**
	* 예약 일련번호
	*/
	private Integer reserveSeqPK = null;
	/**
	* 예약 시작 시간
	*/
	private String reserveStart = "";
	/**
	* 예약 종료 시간
	*/
	private String reserveEnd = "";
	/**
	* 예약 종료 여부
	*/
	private String reserveEndCheck = "";
	/**
	* 예약자
	*/
	private String userName = "";
	/**
	* 예약명
	*/
	private String reserveName = "";

	public RES1000S1Response_Body_reserveList() {
	}

	public RES1000S1Response_Body_reserveList(JsonNode jsonNode) {
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
		this.reserveStart = jsonNode.path("reserveStart").textValue();
		this.reserveEnd = jsonNode.path("reserveEnd").textValue();
		this.userName = jsonNode.path("userName").textValue();
		this.reserveName = jsonNode.path("reserveName").textValue();
	}

	@JsonProperty("reserveSeqPK")
	public Integer getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(Integer reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
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

	@JsonProperty("userName")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("reserveName")
	public String getReserveName() {
		return this.reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}


	public String getReserveEndCheck() {
		return reserveEndCheck;
	}

	public void setReserveEndCheck(String reserveEndCheck) {
		this.reserveEndCheck = reserveEndCheck;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1000S1Response_Body_reserveList [");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append(", ");
		builder.append("reserveStart=").append(reserveStart);
		builder.append(", ");
		builder.append("reserveEnd=").append(reserveEnd);
		builder.append(", ");
		builder.append("userName=").append(userName);
		builder.append(", ");
		builder.append("reserveName=").append(reserveName);
		builder.append("]");
		return builder.toString();
	}

}