package model.MYP1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 예약정보
 */
public class MYP1000S1Response_Body_reserveList {
	/**
	* 회의 종료 시간
	*/
	private String reserveEnd = "";
	/**
	* 회의 시작 시간
	*/
	private String reserveStart = "";
	/**
	* 회의 종료 여부(T 종료 / F 미종료)
	*/
	private String reserveEndCheck = "";
	/**
	* 예약 날짜
	*/
	private String reserveDate = "";
	/**
	* 참석자 수
	*/
	private int attendSize = 0;
	/**
	* 예약 일련번호
	*/
	private int reserveSeqPK = 0;
	/**
	* 예약자
	*/
	private String reserver = "";
	/**
	* 회의명
	*/
	private String reserveName = "";
	/**
	* 회의실
	*/
	private String reserveRoom = "";

	public MYP1000S1Response_Body_reserveList() {
	}

	public MYP1000S1Response_Body_reserveList(JsonNode jsonNode) {
		this.reserveEnd = jsonNode.path("reserveEnd").textValue();
		this.reserveStart = jsonNode.path("reserveStart").textValue();
		this.reserveEndCheck = jsonNode.path("reserveEndCheck").textValue();
		this.reserveDate = jsonNode.path("reserveDate").textValue();
		this.attendSize = jsonNode.path("attendSize").intValue();
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
		this.reserver = jsonNode.path("reserver").textValue();
		this.reserveName = jsonNode.path("reserveName").textValue();
		this.reserveRoom = jsonNode.path("reserveRoom").textValue();
	}

	@JsonProperty("reserveEnd")
	public String getReserveEnd() {
		return this.reserveEnd;
	}

	public void setReserveEnd(String reserveEnd) {
		this.reserveEnd = reserveEnd;
	}

	@JsonProperty("reserveStart")
	public String getReserveStart() {
		return this.reserveStart;
	}

	public void setReserveStart(String reserveStart) {
		this.reserveStart = reserveStart;
	}

	@JsonProperty("reserveEndCheck")
	public String getReserveEndCheck() {
		return this.reserveEndCheck;
	}

	public void setReserveEndCheck(String reserveEndCheck) {
		this.reserveEndCheck = reserveEndCheck;
	}

	@JsonProperty("reserveDate")
	public String getReserveDate() {
		return this.reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	@JsonProperty("attendSize")
	public int getAttendSize() {
		return this.attendSize;
	}

	public void setAttendSize(int attendSize) {
		this.attendSize = attendSize;
	}

	@JsonProperty("reserveSeqPK")
	public int getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(int reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
	}

	@JsonProperty("reserver")
	public String getReserver() {
		return this.reserver;
	}

	public void setReserver(String reserver) {
		this.reserver = reserver;
	}

	@JsonProperty("reserveName")
	public String getReserveName() {
		return this.reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	@JsonProperty("reserveRoom")
	public String getReserveRoom() {
		return this.reserveRoom;
	}

	public void setReserveRoom(String reserveRoom) {
		this.reserveRoom = reserveRoom;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MYP1000S1Response_Body_reserveList [");
		builder.append("reserveEnd=").append(reserveEnd);
		builder.append(", ");
		builder.append("reserveStart=").append(reserveStart);
		builder.append(", ");
		builder.append("reserveEndCheck=").append(reserveEndCheck);
		builder.append(", ");
		builder.append("reserveDate=").append(reserveDate);
		builder.append(", ");
		builder.append("attendSize=").append(attendSize);
		builder.append(", ");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append(", ");
		builder.append("reserver=").append(reserver);
		builder.append(", ");
		builder.append("reserveName=").append(reserveName);
		builder.append(", ");
		builder.append("reserveRoom=").append(reserveRoom);
		builder.append("]");
		return builder.toString();
	}
}